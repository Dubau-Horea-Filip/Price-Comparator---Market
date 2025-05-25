package com.price_comparator.Controller;

import com.price_comparator.Domain.DTO.DiscountDTO;
import com.price_comparator.Domain.Discount;
import com.price_comparator.Repository.DiscountRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/discounts")
public class DiscountController {

    private final DiscountRepository discountRepository;

    public DiscountController(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    @GetMapping
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
