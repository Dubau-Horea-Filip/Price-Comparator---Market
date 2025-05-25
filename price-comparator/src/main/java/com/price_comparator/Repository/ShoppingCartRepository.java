package com.price_comparator.Repository;

import com.price_comparator.Domain.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    Optional<ShoppingCart> findByUserId(Integer userId);
    void deleteByUserId(Integer userId);

}
