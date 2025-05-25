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
        LocalDate yesterday = today.minusDays(1);
        return mapToDTOList(discountRepository.findDiscountsFromTodayOrYesterday(today, yesterday));
    }

    public List<DiscountDTO> getAllDiscounts() {
        return mapToDTOList(discountRepository.findAll());
    }

    public List<DiscountDTO> getBestDiscounts() {
        return mapToDTOList(discountRepository.findTop10ByOrderByPercentageOfDiscountDesc());
    }

    private List<DiscountDTO> mapToDTOList(List<Discount> discounts) {
        return discounts.stream()
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
    }
}