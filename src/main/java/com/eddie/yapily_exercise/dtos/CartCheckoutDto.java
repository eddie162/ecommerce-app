package com.eddie.yapily_exercise.dtos;

import lombok.Value;

import java.io.Serializable;

@Value
public class CartCheckoutDto implements Serializable {
    CartDto cart;
    Double total_cost;
}
