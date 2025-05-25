package com.price_comparator.Controller;

import com.price_comparator.Domain.DTO.DiscountDTO;
import com.price_comparator.Domain.Discount;
import com.price_comparator.Repository.DiscountRepository;
import com.price_comparator.Service.DiscountService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/discounts")
public class DiscountController {


    private final DiscountService discountService;

    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }


    @Operation(
            summary = "All Discounts"
    )
    @GetMapping
    public ResponseEntity<List<DiscountDTO>> getAllDiscounts() {
        List<DiscountDTO> discounts = this.discountService.getAllDiscounts();
        return ResponseEntity.ok(discounts);
    }

    @Operation(
            summary = "New Discounts",
            description = "List discounts that have been newly added (e.g., within the last 24 hours)."
    )
    @GetMapping("/new")
    public ResponseEntity<List<DiscountDTO>> getNewDiscountsFromYesterday() {
        List<DiscountDTO> discounts = discountService.getNewDiscountsFromYesterday();
        return ResponseEntity.ok(discounts);
    }

    @Operation(
            summary = "Best Discounts",
            description = " List products with the highest current percentage discounts across all tracked stores."
    )
    @GetMapping("/best")
    public ResponseEntity<List<DiscountDTO>> getbestDiscounts() {
        List<DiscountDTO> discounts = discountService.getBestDiscounts();
        return ResponseEntity.ok(discounts);
    }
}
