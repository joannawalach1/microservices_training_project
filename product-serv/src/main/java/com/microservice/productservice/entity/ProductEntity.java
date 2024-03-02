package com.microservice.productservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "products")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class ProductEntity extends Product {
    @ManyToOne
    @JoinColumn(name = "category_parameters")
    private Category category;

    public ProductEntity(Category category) {
        this.category = category;
    }
}
