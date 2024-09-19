package com.eddie.ecommerce.controllers;


import com.eddie.ecommerce.dtos.ProductDto;
import com.eddie.ecommerce.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

import static com.eddie.ecommerce.exceptions.GlobalExceptionHandler.generateErrorResponse;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService= productService;
    }

    @GetMapping(value="/")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDto> listAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable Long id){
        ProductDto productDto = productService.getById(id);
        if (Objects.isNull(productDto)) {
            return new ResponseEntity<>(generateErrorResponse("Product Id does not exist"), HttpStatus.BAD_REQUEST);        }
        return ResponseEntity.ok(productService.getById(id));
    }

    @PostMapping(value="/")
    public ResponseEntity<Object> addProduct(@RequestBody ProductDto productDto) {
        ProductDto updatedProductDto = productService.addProduct(productDto);
        return new ResponseEntity<>(updatedProductDto, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable Long id){
        if (productService.deleteProduct(id)){
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(generateErrorResponse("delete for product was unsuccessful"), HttpStatus.BAD_REQUEST);

    }

}
