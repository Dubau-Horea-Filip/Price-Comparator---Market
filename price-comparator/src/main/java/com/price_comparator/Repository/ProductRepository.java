package com.price_comparator.Repository;

import com.price_comparator.Domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findByProductId(String productId);
    Product findByProductIdAndStore(String productId, String store);
    Optional<Product> findTopByProductIdOrderByPriceAsc(String productId);

}