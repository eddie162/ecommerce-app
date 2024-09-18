package com.eddie.yapily_exercise.Controllers;


import com.eddie.yapily_exercise.models.dtos.ProductDto;
import com.eddie.yapily_exercise.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
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
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id){
        return new ResponseEntity<>(productService.getById(id),HttpStatus.OK);
    }

    @PostMapping(value="/")
    public ResponseEntity<Object> addProduct(@RequestBody ProductDto productDto) {

        try {
            ProductDto updatedProductDto = productService.addProduct(productDto);
            return new ResponseEntity<>(updatedProductDto, HttpStatus.CREATED);
        } catch (Exception e) {
            // Prepare error response
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable Long id){
        if (productService.deleteProduct(id)){
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }

        // Prepare error response
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "delete for product was unsuccessful");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

    }

}
