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
        Product product = products.get(id);
        if (product == null) {
            throw new NotFoundException("Product not found with id: " + id);
        }
        return productMapper.toResponse(product);
    }

    public List<ProductResponse> getProducts(
            String name,
            ProductCategory category,
            BigDecimal startPrice,
            BigDecimal endPrice
    ) {
        if (name != null) {
            return getByName(name);
        } else if (category != null) {
            return getByCategory(category);
        } else if (startPrice != null && endPrice != null) {
            return getByPriceRange(startPrice, endPrice);
        } else {
            return products.values().stream()
                    .map(productMapper::toResponse)
                    .toList();
        }
    }

    private List<ProductResponse> getByCategory(ProductCategory category) {
        return products.values().stream()
                .filter(product -> product.getCategory() == category)
                .map(productMapper::toResponse)
                .toList();
    }

    private List<ProductResponse> getByPriceRange(BigDecimal startPrice, BigDecimal endPrice) {
        validatePriceRange(startPrice, endPrice);
        return products.values().stream()
                .filter(product -> product.getPrice().compareTo(startPrice) >= 0
                        && product.getPrice().compareTo(endPrice) <= 0)
                .map(productMapper::toResponse)
                .toList();
    }

    private List<ProductResponse> getByName(String name) {
        return products.values().stream()
                .filter(product -> product.getName().contains(name))
                .map(productMapper::toResponse)
                .toList();
    }

    private void validatePriceRange(BigDecimal startPrice, BigDecimal endPrice) {
        if (startPrice.compareTo(endPrice) > 0) {
            throw new IllegalArgumentException("Start price must be less than or equal to end price.");
        }
    }
}
