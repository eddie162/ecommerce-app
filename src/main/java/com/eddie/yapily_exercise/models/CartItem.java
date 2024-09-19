package com.eddie.yapily_exercise.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

    @EmbeddedId
    private CartItemPK id;

    private Integer quantity;

    public CartItem(Cart cart, Product product, Integer quantity) {
        this.id = CartItemPK.builder()
                .cart(cart)
                .product(product)
                .build();
        this.quantity = quantity;
    }
}