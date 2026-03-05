package com.ironhack.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Product {
    private final UUID id;
    private String name;
    private BigDecimal price;
    private ProductCategory category;
    private Integer quantity;

    public Product(String name, BigDecimal price, ProductCategory category, Integer quantity) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.price = price;
        this.category = category;
        this.quantity = quantity;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
