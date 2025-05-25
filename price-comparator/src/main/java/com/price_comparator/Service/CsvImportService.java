package com.price_comparator.Service;


import com.price_comparator.Domain.Discount;
import com.price_comparator.Domain.Product;
import com.price_comparator.Repository.DiscountRepository;
import com.price_comparator.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;

@Service
public class CsvImportService {

    @Autowired
    private ProductRepository productRepository;


    @Autowired
    private DiscountRepository discountRepository;

    public void importProducts(MultipartFile file, String store) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            reader.readLine(); // Skip header
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(";");
                Product product = new Product();
                product.setProductId(tokens[0]);
                product.setProductName(tokens[1]);
                product.setProductCategory(tokens[2]);
                product.setBrand(tokens[3]);
                product.setPackageQuantity(Double.valueOf((tokens[4])));
                product.setPackageUnit(tokens[5]);
                product.setPrice(Double.parseDouble(tokens[6]));
                product.setCurrency(tokens[7]);
                product.setStore(store);
                productRepository.save(product);
            }
        }
    }

    public void importDiscounts(MultipartFile file, String storeName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            reader.readLine(); // Skip header
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(";");
                Discount discount = new Discount();
                discount.setProductId(tokens[0]);
                discount.setProductName(tokens[1]);
                discount.setBrand(tokens[2]);
                discount.setPackageQuantity(Double.parseDouble(tokens[3]));
                discount.setPackageUnit(tokens[4]);
                discount.setProductCategory(tokens[5]);
                discount.setFromDate(LocalDate.parse(tokens[6]));
                discount.setToDate(LocalDate.parse(tokens[7]));
                discount.setPercentageOfDiscount(Integer.parseInt(tokens[8]));
                discount.setStore(storeName);
                Product product = productRepository.findByProductIdAndStore(tokens[0],storeName);
                discount.setProduct(product);

                discountRepository.save(discount);
            }
        }
    }
}

