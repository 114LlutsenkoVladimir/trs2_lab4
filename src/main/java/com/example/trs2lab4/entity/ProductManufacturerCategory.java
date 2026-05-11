package com.example.trs2lab4.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProductManufacturerCategory implements Serializable {
    private Long id;
    private String name;
    private BigDecimal price;
    private Long categoryId;
    private String categoryName;
    private Long manufacturerId;
    private String manufacturerName;

    public ProductManufacturerCategory(Long id, String name,
                                       BigDecimal price, Long categoryId,
                                       String categoryName,
                                       Long manufacturerId,
                                       String manufacturerName) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.manufacturerId = manufacturerId;
        this.manufacturerName = manufacturerName;
    }

    public Long getId() {
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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Long manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }
}
