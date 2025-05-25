package com.price_comparator.Service;

import com.price_comparator.Domain.DTO.DiscountDTO;
import com.price_comparator.Domain.Discount;
import com.price_comparator.Repository.DiscountRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiscountService {

    private final DiscountRepository discountRepository;

    public DiscountService(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    public List<DiscountDTO> getNewDiscountsFromYesterday() {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = LocalDate.now().minusDays(1);
        List<DiscountDTO> dtos = discountRepository.findDiscountsFromTodayOrYesterday(today,yesterday)
                .stream()
                .map(d -> new DiscountDTO(
                        d.getProductId(),
                        d.getProductName(),
                        d.getBrand(),
                        d.getPackageQuantity(),
                        d.getPackageUnit(),
                        d.getProductCategory(),
                        d.getFromDate(),
                        d.getToDate(),
                        d.getPercentageOfDiscount(),
                        d.getStoreName()
                ))
                .collect(Collectors.toList());

        return dtos;
    }

    public ResponseEntity<List<DiscountDTO>> getAllDiscounts() {
        List<DiscountDTO> dtos = discountRepository.findAll()
                .stream()
                .map(d -> new DiscountDTO(
                        d.getProductId(),
                        d.getProductName(),
                        d.getBrand(),
                        d.getPackageQuantity(),
                        d.getPackageUnit(),
                        d.getProductCategory(),
                        d.getFromDate(),
                        d.getToDate(),
                        d.getPercentageOfDiscount(),
                        d.getStoreName()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }
}
