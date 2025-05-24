package com.price_comparator.Domain;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.persistence.Id;

@Entity
@Table(name = "shopping_cart_items")
@Data
public class ShoppingCartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private ShoppingCart cart;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;

    private double unitPrice;

    private double totalPrice;
}
