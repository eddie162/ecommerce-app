package com.eddie.ecommerce.service;

import com.eddie.ecommerce.exceptions.GenericAPIException;
import com.eddie.ecommerce.models.Label;
import com.eddie.ecommerce.models.Product;
import com.eddie.ecommerce.dtos.ProductDto;
import com.eddie.ecommerce.repositories.LabelRepository;
import com.eddie.ecommerce.repositories.ProductRepository;
import com.eddie.ecommerce.service.mappers.DtoEntityMapper;
import com.eddie.ecommerce.service.mappers.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductService {

    public static final DateTimeFormatter PRODUCT_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    private final ProductRepository productRepository;
    private final LabelRepository labelRepository;
    private final DtoEntityMapper<ProductDto,Product> productMapper;
    private final Clock clock;

    public ProductService(ProductRepository productRepository, LabelRepository labelRepository, DtoEntityMapper<ProductDto, Product> productMapper, Clock clock) {
        this.productRepository = productRepository;
        this.labelRepository = labelRepository;
        this.productMapper = productMapper;
        this.clock = clock;
    }


    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::entityToDto)
                .toList();
    }

    public ProductDto getById(Long id) {
        Optional<Product> optProduct = productRepository.findById(id);
        if (optProduct.isEmpty()){
            throw new GenericAPIException("Product Id does not exist");
        }
        return productMapper.entityToDto(optProduct.get());

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
        entity.setAddedAt(LocalDate.now(this.clock));
        productRepository.save(entity);
        return productMapper.entityToDto(entity);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
