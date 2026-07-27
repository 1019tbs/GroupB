package com.example.demo.model;

import java.math.BigDecimal;

public class CartItem_oonaka {

    private Product_oonaka product;
    private int quantity;

    public CartItem_oonaka() {
    }

    public CartItem_oonaka(
            Product_oonaka product,
            int quantity) {

        this.product = product;
        this.quantity = quantity;
    }

    public Product_oonaka getProduct() {
        return product;
    }

    public void setProduct(
            Product_oonaka product) {

        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(
            int quantity) {

        this.quantity = quantity;
    }

    /*
     * 商品の小計を計算する
     * 価格 × 数量
     */
    public BigDecimal getSubtotal() {

        return product.getPrice()
                .multiply(
                        BigDecimal.valueOf(quantity));
    }
}
