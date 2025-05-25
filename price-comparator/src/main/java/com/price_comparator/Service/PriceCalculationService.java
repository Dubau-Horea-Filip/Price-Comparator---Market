package com.price_comparator.Service;

import com.price_comparator.Domain.DTO.FinalPriceDTO;
import com.price_comparator.Domain.Discount;
import com.price_comparator.Domain.Product;
import com.price_comparator.Repository.DiscountRepository;
import com.price_comparator.Repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PriceCalculationService {

    private final DiscountRepository discountRepository;
    private final ProductRepository productRepository;

    public PriceCalculationService(DiscountRepository discountRepository, ProductRepository productRepository) {
        this.discountRepository = discountRepository;
        this.productRepository = productRepository;
    }

    public List<FinalPriceDTO> getLowestFinalPrice(String productId) {
        LocalDate now = LocalDate.now();
        now = LocalDate.of(2025, 5, 5); //for testing purposes, set a specific date

        List<Discount> discounts = discountRepository
                .findActiveDiscountsForProductOnDate(productId, now);

        if (discounts.isEmpty()) {
            throw new RuntimeException("No active discounts found for product: " + productId);
        }

        List<FinalPriceDTO> prices = new ArrayList<>();

        for (Discount discount : discounts) {
            Product product = productRepository.findByProductIdAndStore(productId, discount.getStoreName());

            if (product!= null) {

                double finalPrice = product.getPrice() * (1 - discount.getPercentageOfDiscount() / 100.0);

                prices.add(new FinalPriceDTO(
                        discount.getStoreName(),
                        product.getProductName(),
                        product.getProductId(),
                        finalPrice
                ));
            }
        }

        double min = prices.stream()
                .mapToDouble(FinalPriceDTO::getFinalPrice)
                .min()
                .orElseThrow();

        return prices.stream()
                .filter(p -> p.getFinalPrice() == min)
                .collect(Collectors.toList());
    }

}
