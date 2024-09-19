package com.eddie.ecommerce.controllers;


import com.eddie.ecommerce.dtos.ProductDto;
import com.eddie.ecommerce.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.eddie.ecommerce.exceptions.GlobalExceptionHandler.generateErrorResponse;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService= productService;
    }

    @GetMapping(value="/")
    public List<ProductDto> listAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping(value="/{id}")
    public ProductDto getProductById(@PathVariable Long id){
        return productService.getById(id);
    }

    @PostMapping(value="/")
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto) {
        ProductDto updatedProductDto = productService.addProduct(productDto);
        return new ResponseEntity<>(updatedProductDto, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

}
