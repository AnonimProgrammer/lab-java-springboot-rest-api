package com.ironhack.controller;

import com.ironhack.dto.request.ProductRequest;
import com.ironhack.dto.response.ProductResponse;
import com.ironhack.model.ProductCategory;
import com.ironhack.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(
           @Valid @RequestBody ProductRequest request
    ) {
        ProductResponse createdProduct = productService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(productService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) ProductCategory category,
            @RequestParam(required = false) BigDecimal startPrice,
            @RequestParam(required = false) BigDecimal endPrice
    ) {
        List<ProductResponse> products = productService.getProducts(name, category, startPrice, endPrice);
        return ResponseEntity.ok(products);
    }
}
