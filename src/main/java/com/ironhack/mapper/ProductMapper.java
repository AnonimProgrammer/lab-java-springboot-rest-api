package com.ironhack.mapper;

import com.ironhack.dto.request.ProductRequest;
import com.ironhack.dto.response.ProductResponse;
import com.ironhack.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public Product toModel(ProductRequest request) {
        return new Product(
                request.name(),
                request.price(),
                request.category(),
                request.quantity()
        );
    }

    public ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getCategory(),
                product.getQuantity()
        );
    }
}
