package com.price_comparator.Controller;

import com.price_comparator.Domain.DTO.FinalPriceDTO;
import com.price_comparator.Service.PriceCalculationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prices")
public class PriceCalculationController {

    private final PriceCalculationService priceCalculationService;

    public PriceCalculationController(PriceCalculationService priceCalculationService) {
        this.priceCalculationService = priceCalculationService;
    }

    @Operation(
            summary = "Get the lowest final price for a product across all stores",
            description = "Returns the store(s) offering the lowest final price (after discount) for the specified product ID."
    )
    @ApiResponse(responseCode = "200", description = "Successfully retrieved lowest price")
    @ApiResponse(responseCode = "404", description = "No active discounts found")
    @GetMapping("/lowest")
    public ResponseEntity<List<FinalPriceDTO>> getLowestPrice(@RequestParam String productId) {
        List<FinalPriceDTO> result = priceCalculationService.getLowestFinalPrice(productId);
        return ResponseEntity.ok(result);
    }
}
