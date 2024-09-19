package com.eddie.ecommerce.service;

import com.eddie.ecommerce.exceptions.GenericAPIException;
import com.eddie.ecommerce.models.Cart;
import com.eddie.ecommerce.models.CartItem;
import com.eddie.ecommerce.dtos.CartCheckoutDto;
import com.eddie.ecommerce.dtos.CartDto;
import com.eddie.ecommerce.dtos.CartItemDto;
import com.eddie.ecommerce.models.Product;
import com.eddie.ecommerce.repositories.CartItemRepository;
import com.eddie.ecommerce.repositories.CartRepository;
import com.eddie.ecommerce.repositories.ProductRepository;
import com.eddie.ecommerce.service.mappers.DtoEntityMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

    @Transactional
    public CartDto createCart(CartDto inputDto) {
        Cart cart = new Cart();
        cart = cartRepository.save(cart);

        Set<CartItem> cartItems = new HashSet<>();
        for (CartItemDto cartItemDto :  inputDto.getCartItems()) {
            validateProductAndAddToSet(cart, cartItems, cartItemDto);
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

    @Transactional
    public CartDto modifyCart(Long cartId, List<CartItemDto> inputCartItems) {
        Optional<Cart> optCart = cartRepository.findById(cartId);

        if (optCart.isEmpty()) {
            throw new GenericAPIException("Provided CartId does not exist");
        }

        Cart cart = optCart.get();

        if (cart.isCheckedOut()) {
            throw new GenericAPIException("Cart is already checked out and cannot be modified");
        }

        Set<CartItem> cartItems = new HashSet<>();
        for (CartItemDto cartItemDto :  inputCartItems) {
            validateProductAndAddToSet(cart, cartItems, cartItemDto);
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

    private void validateProductAndAddToSet(Cart cart, Set<CartItem> cartItems, CartItemDto cartItemDto) {
        Optional<Product> product = productRepository.findById(cartItemDto.getId());
        if (product.isEmpty()){
            throw new GenericAPIException("Product provided does not exist");
        }
        CartItem cartItem = new CartItem(cart, product.get(), cartItemDto.getQuantity());
        cartItem = cartItemRepository.save(cartItem);
        cartItems.add(cartItem);
    }
}
