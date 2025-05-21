package com.price_comparator.Domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "discounts")
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id_ref")
    private Product product;

    private String productId;
    private String productName;
    private String brand;
    private double packageQuantity;
    private String packageUnit;
    private String productCategory;
    private LocalDate fromDate;
    private LocalDate toDate;
    private int percentageOfDiscount;

}
