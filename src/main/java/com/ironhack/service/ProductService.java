package com.ironhack.service;

import com.ironhack.dto.request.ProductRequest;
import com.ironhack.dto.response.ProductResponse;
import com.ironhack.exception.NotFoundException;
import com.ironhack.mapper.ProductMapper;
import com.ironhack.model.Product;
import com.ironhack.model.ProductCategory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class ProductService {
    private final Map<UUID, Product> products = new HashMap<>();

    private final ProductMapper productMapper;

    public ProductService(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public ProductResponse create(ProductRequest request) {
        Product product = productMapper.toModel(request);
        products.put(product.getId(), product);
        return productMapper.toResponse(product);
    }

    public ProductResponse getById(UUID id) {
        Product product = findOrThrow(id);
        return productMapper.toResponse(product);
    }

    public List<ProductResponse> getProducts(
            String name,
            ProductCategory category,
            BigDecimal startPrice,
            BigDecimal endPrice
    ) {
        return products.values().stream()
                .filter(product -> name == null || product.getName().contains(name))
                .filter(product -> category == null || product.getCategory() == category)
                .filter(product -> matchesPriceRange(product, startPrice, endPrice))
                .map(productMapper::toResponse)
                .toList();
    }

    public ProductResponse update(UUID id, ProductRequest request) {
        Product existingProduct = findOrThrow(id);

        existingProduct.setName(request.name());
        existingProduct.setPrice(request.price());
        existingProduct.setCategory(request.category());
        existingProduct.setQuantity(request.quantity());

        return productMapper.toResponse(existingProduct);
    }

    public void delete(UUID id) {
        findOrThrow(id);
        products.remove(id);
    }

    private Product findOrThrow(UUID id) {
        Product product = products.get(id);
        if (product == null) {
            throw new NotFoundException("Product not found with id: " + id);
        }
        return product;
    }

    private void validatePriceRange(BigDecimal startPrice, BigDecimal endPrice) {
        if (startPrice.compareTo(endPrice) > 0) {
            throw new IllegalArgumentException("Start price must be less than or equal to end price.");
        }
    }

    private boolean matchesPriceRange(Product product, BigDecimal startPrice, BigDecimal endPrice) {
        if (startPrice != null && endPrice != null) {
            validatePriceRange(startPrice, endPrice);
            return product.getPrice().compareTo(startPrice) >= 0 &&
                    product.getPrice().compareTo(endPrice) <= 0;
        }
        if (startPrice != null) {
            return product.getPrice().compareTo(startPrice) >= 0;
        }
        if (endPrice != null) {
            return product.getPrice().compareTo(endPrice) <= 0;
        }
        return true;
    }
}
