package com.eddie.yapily_exercise.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
public class Cart {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "id.cart")
    private Set<CartItem> products;

    private boolean checkedOut = false;
}