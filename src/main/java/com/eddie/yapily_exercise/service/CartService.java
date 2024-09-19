package com.eddie.yapily_exercise.service;

import com.eddie.yapily_exercise.exceptions.GenericAPIException;
import com.eddie.yapily_exercise.models.Cart;
import com.eddie.yapily_exercise.models.CartItem;
import com.eddie.yapily_exercise.dtos.CartCheckoutDto;
import com.eddie.yapily_exercise.dtos.CartDto;
import com.eddie.yapily_exercise.dtos.CartItemDto;
import com.eddie.yapily_exercise.repositories.CartItemRepository;
import com.eddie.yapily_exercise.repositories.CartRepository;
import com.eddie.yapily_exercise.repositories.ProductRepository;
import com.eddie.yapily_exercise.service.mappers.DtoEntityMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class CartService{

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;


    private final DtoEntityMapper<CartDto,Cart> cartMapper;

    public CartService(CartRepository cartRepository
            , DtoEntityMapper<CartDto, Cart> cartMapper
            , ProductRepository productRepository
            , CartItemRepository cartItemRepository){
        this.cartRepository = cartRepository;
        this.cartMapper = cartMapper;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
    }

    public CartDto createCart(CartDto inputDto) {
        Cart cart = new Cart();
        cart = cartRepository.save(cart);

        Set<CartItem> cartItems = new HashSet<>();
        for (CartItemDto cartItemDto :  inputDto.getCartItems()) {
            CartItem cartItem = new CartItem(cart, productRepository.findById(cartItemDto.getId()).orElse(null), cartItemDto.getQuantity());
            cartItem = cartItemRepository.save(cartItem);
            cartItems.add(cartItem);
        }
        cart.setProducts(cartItems);

        cart = cartRepository.save(cart);

        return cartMapper.entityToDto(cart);

    }

    public List<CartDto> listAllCarts() {
        return cartRepository.findAll()
                .stream()
                .map(cartMapper::entityToDto)
                .toList();
    }

    public CartDto modifyCart(Long cartId, List<CartItemDto> inputCartItems) {
        Cart cart = cartRepository.findById(cartId).orElse(null);

        if (Objects.isNull(cart)) {
            throw new GenericAPIException("Provided CartId does not exist");
        }

        if (cart.isCheckedOut()) {
            throw new GenericAPIException("Cart is already checked out and cannot be modified");
        }

        Set<CartItem> cartItems = new HashSet<>();
        for (CartItemDto cartItemDto :  inputCartItems) {
            CartItem cartItem = new CartItem(cart, productRepository.findById(cartItemDto.getId()).orElse(null), cartItemDto.getQuantity());
            cartItem = cartItemRepository.save(cartItem);
            cartItems.add(cartItem);
        }

        cart.setProducts(cartItems);
        cartRepository.save(cart);
        return cartMapper.entityToDto(cart);

    }

    public CartCheckoutDto checkoutCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElse(null);
        cart.setCheckedOut(true);
        cartRepository.save(cart);

        Double totalCost = cart.getProducts().stream()
                .map(cartItem -> {
                    Double product_cost = cartItem.getId().getProduct().getPrice();
                    Integer quantity = cartItem.getQuantity();
                    return product_cost * quantity;
                })
                .reduce(0.0, Double::sum);
        return new CartCheckoutDto(cartMapper.entityToDto(cart), totalCost);
    }
}
