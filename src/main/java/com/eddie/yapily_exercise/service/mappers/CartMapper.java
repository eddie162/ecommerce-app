package com.eddie.yapily_exercise.service.mappers;


import com.eddie.yapily_exercise.models.Cart;
import com.eddie.yapily_exercise.models.CartItem;
import com.eddie.yapily_exercise.models.CartItemPK;
import com.eddie.yapily_exercise.models.Product;
import com.eddie.yapily_exercise.dtos.CartDto;
import com.eddie.yapily_exercise.dtos.CartItemDto;
import com.eddie.yapily_exercise.dtos.ProductDto;
import com.eddie.yapily_exercise.repositories.ProductRepository;

import java.util.Set;
import java.util.stream.Collectors;

public class CartMapper implements DtoEntityMapper<CartDto, Cart> {

    private final DtoEntityMapper<ProductDto, Product> productMapper;
    private final ProductRepository productRepository;

    public CartMapper(DtoEntityMapper<ProductDto, Product> productMapper, ProductRepository productRepository) {
        this.productMapper = productMapper;
        this.productRepository = productRepository;
    }

    public CartDto entityToDto(Cart cart){
        Set<CartItemDto> cartItems = cart.getProducts().stream()
                .map(cartItem -> {
                    CartItemDto cartItemDto = new CartItemDto(cartItem.getId().getProduct().getId(), cartItem.getQuantity());
                    return cartItemDto;
                })
                .collect(Collectors.toSet());

        return new CartDto(cart.getId(),cartItems, cart.isCheckedOut());
    }

    public Cart dtoToEntity(CartDto cartDto){
        Set<CartItem> cartItemDtos = cartDto.getProducts().stream()
                .map(cartItemDto -> {
                    Product product = productRepository.findById(cartItemDto.getProduct_id()).orElse(null);
                    Cart cart = null;
                    CartItemPK pk = new CartItemPK();
                    pk.setCart(cart);
                    pk.setProduct(product);

                    CartItem cartItem = new CartItem();
                    cartItem.setId(pk);
                    cartItem.setQuantity(cartItemDto.getQuantity());
                    return cartItem;
                        })
                .collect(Collectors.toSet());

        Cart cart = new Cart();
        cart.setId(cartDto.getCart_id());
        cart.setProducts(cartItemDtos);
        cart.setCheckedOut(cartDto.getChecked_out());

        return cart;
    }
}
