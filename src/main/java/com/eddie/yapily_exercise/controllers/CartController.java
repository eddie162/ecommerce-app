package com.eddie.yapily_exercise.controllers;

import com.eddie.yapily_exercise.dtos.CartCheckoutDto;
import com.eddie.yapily_exercise.dtos.CartDto;
import com.eddie.yapily_exercise.dtos.CartItemDto;
import com.eddie.yapily_exercise.exceptions.GenericAPIException;
import com.eddie.yapily_exercise.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping(value="/")
    public ResponseEntity<CartDto> createCart(@RequestBody CartDto inputCart) {
        return new ResponseEntity<>(cartService.createCart(inputCart), HttpStatus.CREATED);
    }

    @GetMapping(value = "/")
    public ResponseEntity<List<CartDto>> getAllCarts() {
        return new ResponseEntity<>(cartService.listAllCarts(), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> modifyCart(@PathVariable Long id, @RequestBody List<CartItemDto> cartItems) {
        return new ResponseEntity<>(cartService.modifyCart(id, cartItems), HttpStatus.OK);
    }

    @PostMapping(value= "/{id}/checkout")
    public CartCheckoutDto checkoutCart(@PathVariable Long id) {
        return cartService.checkoutCart(id);
    }

}
