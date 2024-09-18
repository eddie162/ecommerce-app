package com.eddie.yapily_exercise.service;

import com.eddie.yapily_exercise.models.Label;
import com.eddie.yapily_exercise.models.Product;
import com.eddie.yapily_exercise.models.dtos.ProductDto;
import com.eddie.yapily_exercise.repositories.LabelRepository;
import com.eddie.yapily_exercise.repositories.ProductRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    private final ProductRepository productRepository;
    private final LabelRepository labelRepository;

    public ProductService(ProductRepository productRepository, LabelRepository labelRepository) {
        this.productRepository = productRepository;
        this.labelRepository = labelRepository;
    }


    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::entityToDto)
                .toList();
    }

    public ProductDto getById(Long id) {
        return productRepository.findById(id)
                .map(this::entityToDto)
                .orElse(null);
    }

    public ProductDto addProduct(ProductDto productDto) {
        String productName = productDto.getName();
        if (productName.length() > 200){
            throw new RuntimeException("productName cannot exceed 200 characters");
        }

        if (Objects.nonNull(productRepository.findByName(productName))){
            throw new RuntimeException("productName already exist");
        }

        Set<String> validLabelNames = labelRepository.findAll().stream()
                .map(Label::getLabelName)
                .collect(Collectors.toSet());

        if (!validLabelNames.containsAll(productDto.getLabels())) {
            throw new RuntimeException("Label not found");
        }

        Product entity = dtoToEntity(productDto);
        entity.setAddedAt(LocalDate.now());
        productRepository.save(entity);
        return entityToDto(entity);
    }


    public ProductDto entityToDto(Product product){
        List<String> labelList = product.getLabels().stream()
                .map(Label::getLabelName)
                .toList();

        return new ProductDto(product.getId(), product.getName(), product.getPrice(), product.getAddedAt().format(DATE_FORMAT), labelList);
    }

    public Product dtoToEntity(ProductDto productDto){
        List<Label> labels = labelRepository.findAllByLabelNameIn(productDto.getLabels());
        Product productEntity = new Product();
        productEntity.setName(productDto.getName());
        productEntity.setPrice(productDto.getPrice());
        productEntity.setLabels(labels);

        return productEntity;
    }


    public boolean deleteProduct(Long id) {
        try {
            // this will work for non-existing ids.
            productRepository.deleteById(id);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}
