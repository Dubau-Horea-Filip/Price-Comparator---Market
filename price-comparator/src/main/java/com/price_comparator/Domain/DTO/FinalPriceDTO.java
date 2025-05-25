package com.price_comparator.Domain.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FinalPriceDTO {
    private String storeName;
    private String product;
    private String productId;
    private double finalPrice;
}
