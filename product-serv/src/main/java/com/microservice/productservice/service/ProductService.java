package com.microservice.productservice.service;

import com.microservice.productservice.entity.dto.ProductDto;
import com.microservice.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductDto getProductDto() {
        return null;
    }
}
