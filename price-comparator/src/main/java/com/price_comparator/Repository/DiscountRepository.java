package com.price_comparator.Repository;

import com.price_comparator.Domain.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface DiscountRepository extends JpaRepository<Discount, Long> {

    Optional<Discount> findByProductIdAndFromDateLessThanEqualAndToDateGreaterThanEqual(
            String productId, LocalDate from, LocalDate to);
}


