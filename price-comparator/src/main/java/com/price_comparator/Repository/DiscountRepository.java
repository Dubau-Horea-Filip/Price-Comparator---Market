package com.price_comparator.Repository;

import com.price_comparator.Domain.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DiscountRepository extends JpaRepository<Discount, Long> {

    @Query("SELECT d FROM Discount d WHERE d.productId = :productId AND :date BETWEEN d.fromDate AND d.toDate")
    List<Discount> findActiveDiscountsForProductOnDate(@Param("productId") String productId,
                                                       @Param("date") LocalDate date);

    @Query("SELECT d FROM Discount d WHERE d.fromDate = :today OR d.fromDate = :yesterday")
    List<Discount> findDiscountsFromTodayOrYesterday(@Param("today") LocalDate today,
                                                     @Param("yesterday") LocalDate yesterday);


    List<Discount> findTop10ByOrderByPercentageOfDiscountDesc();

}