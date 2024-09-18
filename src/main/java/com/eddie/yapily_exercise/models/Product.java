package com.eddie.yapily_exercise.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;


@Getter
@Setter
@Entity
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 200)
    private String name;

    private Double price;

    private LocalDate addedAt;

    @ManyToMany(fetch= FetchType.EAGER)
    @JoinTable(name = "product_label",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "label_id"))
    private Set<Label> labels;

}