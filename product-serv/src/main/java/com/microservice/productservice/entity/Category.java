package com.microservice.productservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "category_parameters")
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_seq_generator")
    @SequenceGenerator(name = "category_seq_generator", sequenceName = "category_parameters_id_seq", allocationSize = 1)
    private long id;
    @Column(name = "category_name")
    private String name;
    private String short_Id;

}
