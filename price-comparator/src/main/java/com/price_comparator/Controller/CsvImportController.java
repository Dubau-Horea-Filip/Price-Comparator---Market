package com.price_comparator.Controller;

import com.price_comparator.Service.CsvImportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/import")
public class CsvImportController {

    @Autowired
    private CsvImportService csvImportService;

    @Operation(summary = "Upload product CSV", description = "Imports product data from a CSV file.")
    @ApiResponse(responseCode = "200", description = "Successful import")
    @PostMapping(value = "/products", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> importProducts(
            @Parameter(description = "CSV file", required = true)
            @RequestParam("file") MultipartFile file) {
        try {
            csvImportService.importProducts(file);
            return ResponseEntity.ok("Products imported successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error importing products.");
        }
    }


    @PostMapping(value = "/discounts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> importDiscounts(@RequestParam("file") MultipartFile file) {
        try {
            csvImportService.importDiscounts(file);
            return ResponseEntity.ok("Discounts imported successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error importing discounts.");
        }
    }
}

