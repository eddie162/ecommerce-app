package com.eddie.ecommerce.controllers;

import com.eddie.ecommerce.dtos.CartCheckoutDto;
import com.eddie.ecommerce.dtos.CartDto;
import com.eddie.ecommerce.dtos.CartItemDto;
import com.eddie.ecommerce.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<CartDto> getAllCarts() {
        return cartService.listAllCarts();
    }

    @PutMapping(value = "/{id}")
    public CartDto modifyCart(@PathVariable Long id, @RequestBody List<CartItemDto> cartItems) {
        return cartService.modifyCart(id, cartItems);
    }

    @PostMapping(value= "/{id}/checkout")
    public CartCheckoutDto checkoutCart(@PathVariable Long id) {
        return cartService.checkoutCart(id);
    }

}
