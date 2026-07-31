package com.example.demo.model;

import java.math.BigDecimal;

public class CartItem {

    private Product_cart product;
    private int quantity;

    public CartItem() {
    }

    public CartItem(
            Product_cart product,
            int quantity) {

        this.product = product;
        this.quantity = quantity;
    }

    public Product_cart getProduct() {
        return product;
    }

    public void setProduct(
            Product_cart product) {

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
