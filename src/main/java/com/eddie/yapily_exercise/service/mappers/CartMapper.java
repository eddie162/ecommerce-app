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

    public CartMapper() {}

    public CartDto entityToDto(Cart cart){
        Set<CartItemDto> cartItems = cart.getProducts().stream()
                .map(cartItem -> new CartItemDto(cartItem.getId().getProduct().getId(), cartItem.getQuantity()))
                .collect(Collectors.toSet());

        return new CartDto(cart.getId(),cartItems, cart.isCheckedOut());
    }

    public Cart dtoToEntity(CartDto cartDto){
        Set<CartItem> cartItemDtos = cartDto.getCartItems().stream()
                .map(cartItemDto -> CartItem.builder()
                        .quantity(cartItemDto.getQuantity())
                        .build())
                .collect(Collectors.toSet());

        return Cart.builder()
                .id(cartDto.getId())
                .products(cartItemDtos)
                .checkedOut(cartDto.isCheckedOut())
                .build();
    }
}
