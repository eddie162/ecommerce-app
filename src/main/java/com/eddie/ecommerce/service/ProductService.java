package com.eddie.ecommerce.service;

import com.eddie.ecommerce.exceptions.GenericAPIException;
import com.eddie.ecommerce.models.Label;
import com.eddie.ecommerce.models.Product;
import com.eddie.ecommerce.dtos.ProductDto;
import com.eddie.ecommerce.repositories.LabelRepository;
import com.eddie.ecommerce.repositories.ProductRepository;
import com.eddie.ecommerce.service.mappers.DtoEntityMapper;
import com.eddie.ecommerce.service.mappers.ProductMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductService {

    public static final DateTimeFormatter PRODUCT_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    private final ProductRepository productRepository;
    private final LabelRepository labelRepository;
    private final DtoEntityMapper<ProductDto,Product> productMapper = new ProductMapper();

    public ProductService(ProductRepository productRepository, LabelRepository labelRepository) {
        this.productRepository = productRepository;
        this.labelRepository = labelRepository;
    }


    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::entityToDto)
                .toList();
    }

    public ProductDto getById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::entityToDto)
                .orElse(null);
    }

    public ProductDto addProduct(ProductDto productDto) {
        String productName = productDto.getName();
        if (productName.length() > 200){
            throw new GenericAPIException("productName cannot exceed 200 characters");
        }

        if (Objects.nonNull(productRepository.findByName(productName))){
            throw new GenericAPIException("productName already exist");
        }

        Set<String> validLabelNames = labelRepository.findAll().stream()
                .map(Label::getLabelName)
                .collect(Collectors.toSet());

        if (!validLabelNames.containsAll(productDto.getLabels())) {
            throw new GenericAPIException("Label not found");
        }

        Product entity = productMapper.dtoToEntity(productDto);
        Set<Label> labels = labelRepository.findAllByLabelNameIn(productDto.getLabels());

        entity.setLabels(labels);
        entity.setAddedAt(LocalDate.now());
        productRepository.save(entity);
        return productMapper.entityToDto(entity);
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
