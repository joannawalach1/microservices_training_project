package com.microservice.productservice.mediator;

import com.microservice.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMediator {
    private final ProductService productService;
    public ResponseEntity<?> getProduct(int page, int limit) {
        return null;
    }
}
