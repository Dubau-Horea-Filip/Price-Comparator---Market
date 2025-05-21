package com.price_comparator.Domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @Column(unique = true, nullable = false)
    private String productId;
    private String productName;
    private String productCategory;
    private String brand;
    private Double packageQuantity;
    private String packageUnit;
    private Double price;
    private String currency;
}
