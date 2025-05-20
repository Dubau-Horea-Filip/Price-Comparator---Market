package com.price_comparator.Repository;

import com.price_comparator.Domain.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<Discount, Long> {}
