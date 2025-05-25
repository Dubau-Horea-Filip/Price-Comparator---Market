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

    public List<FinalPriceDTO> getLowestFinalPriceOldWorking(String productId) {
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

    public List<FinalPriceDTO> getLowestFinalPrice(String productId) {
        LocalDate now = LocalDate.of(2025, 5, 5); // For testing purposes

        List<Product> products = productRepository.findByProductId(productId);

        if (products.isEmpty()) {
            throw new RuntimeException("No products found for product: " + productId);
        }

        List<FinalPriceDTO> finalPrices = new ArrayList<>();

        for (Product product : products) {
            List<Discount> discounts = discountRepository.findActiveDiscountsForProductOnDate(productId, now)
                    .stream()
                    .filter(d -> d.getStoreName().equals(product.getStore()))
                    .toList();

            double originalPrice = product.getPrice();
            double finalPrice;

            if (!discounts.isEmpty()) {
                // Apply the best available discount
                int maxDiscount = discounts.stream()
                        .mapToInt(Discount::getPercentageOfDiscount)
                        .max()
                        .orElse(0);

                finalPrice = originalPrice * (1 - maxDiscount / 100.0);
            } else {
                finalPrice = originalPrice;
            }

            finalPrices.add(new FinalPriceDTO(
                    product.getStore(),
                    product.getProductName(),
                    product.getProductId(),
                    finalPrice
            ));
        }

        // Find the lowest final price
        double min = finalPrices.stream()
                .mapToDouble(FinalPriceDTO::getFinalPrice)
                .min()
                .orElseThrow();
        List<FinalPriceDTO> result = finalPrices.stream()
                .filter(p -> p.getFinalPrice() == min)
                .collect(Collectors.toList());

        return result.isEmpty() ?
                List.of(new FinalPriceDTO("No Store", "No Product", productId, 0.0)) :
                result;
    }
}