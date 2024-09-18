package com.eddie.yapily_exercise.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class CartItem {

    @EmbeddedId
    private CartItemPK id;

    private Integer quantity;

    public CartItem(Cart cart, Product product, Integer quantity) {
        id = new CartItemPK();
        id.setCart(cart);
        id.setProduct(product);
        this.quantity = quantity;
    }

    public CartItem() {

    }
}