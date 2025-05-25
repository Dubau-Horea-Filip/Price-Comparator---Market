package com.price_comparator.Domain.DTO;

public class ProductValueDTO {
    private String productId;
    private String productName;
    private String storeName;
    private double pricePerUnit;
    private double totalPrice;
    private double packSize;
    private String unit;

    public ProductValueDTO(String productId, String productName, String storeName, double pricePerUnit, double totalPrice, double packSize, String unit) {
        this.productId = productId;
        this.productName = productName;
        this.storeName = storeName;
        this.pricePerUnit = pricePerUnit;
        this.totalPrice = totalPrice;
        this.packSize = packSize;
        this.unit = unit;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getPackSize() {
        return packSize;
    }

    public void setPackSize(double packSize) {
        this.packSize = packSize;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
