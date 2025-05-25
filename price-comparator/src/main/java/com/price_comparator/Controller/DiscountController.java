package com.price_comparator.Controller;

import com.price_comparator.Domain.DTO.DiscountDTO;
import com.price_comparator.Domain.Discount;
import com.price_comparator.Repository.DiscountRepository;
import com.price_comparator.Service.DiscountService;
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

    @GetMapping
    public ResponseEntity<List<DiscountDTO>> getAllDiscounts()
    {
        return this.discountService.getAllDiscounts();
    }

    @GetMapping("/new")
    public ResponseEntity<List<DiscountDTO>> getNewDiscountsFromYesterday() {
        List<DiscountDTO> discounts = discountService.getNewDiscountsFromYesterday();
        return ResponseEntity.ok(discounts);
    }
}
