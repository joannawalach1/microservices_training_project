package com.microservice.productservice.fasada;

import com.microservice.productservice.mediator.ProductMediator;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductMediator productMediator;
    @GetMapping
    public ResponseEntity<?> getProduct(HttpServletRequest request, @RequestParam(required = false, defaultValue = "0") int page,
                                        @RequestParam(required = false, defaultValue = "16") int limit) {
        productMediator.getProduct(page, limit);
        return null;
    }
}
