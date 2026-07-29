package com.example.demo.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public class Product_oonaka {

    private long productId;
    private String productName;
    private BigDecimal price;
    private int stock;
    private int categoryId;
    private String description;
    private String imageUrl;

    /*
     * true：取扱中
     * false：取扱停止
     */
    private boolean active = true;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Product_oonaka() {
    }

    /**
     * 既存コードとの互換性を維持するコンストラクタです。
     */
    public Product_oonaka(
            long productId,
            String productName,
            BigDecimal price,
            int stock,
            int categoryId,
            String description,
            String imageUrl,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {

        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.stock = stock;
        this.categoryId = categoryId;
        this.description = description;
        this.imageUrl = imageUrl;
        this.active = true;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(
            long productId) {

        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(
            String productName) {

        this.productName = productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(
            BigDecimal price) {

        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(
            int stock) {

        this.stock = stock;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(
            int categoryId) {

        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(
            String description) {

        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(
            String imageUrl) {

        this.imageUrl = imageUrl;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(
            boolean active) {

        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(
            LocalDateTime createdAt) {

        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(
            LocalDateTime updatedAt) {

        this.updatedAt = updatedAt;
    }
}