package com.eddie.yapily_exercise.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "id.cart")
    private Set<CartItem> products;

    @Builder.Default
    private boolean checkedOut = false;
}