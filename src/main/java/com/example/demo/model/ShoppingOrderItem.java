package com.example.demo.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ショッピング注文の商品明細1件分を保持します。
 */
@Getter
@Setter
@NoArgsConstructor
public class ShoppingOrderItem {

    private Long shoppingOrderItemId;
    private Long shoppingOrderId;
    private Long productId;
    private String productName;
    private BigDecimal unitPrice;
    private Integer quantity;

    public BigDecimal getSubtotal() {

        if (unitPrice == null
                || quantity == null) {

            return BigDecimal.ZERO;
        }

        return unitPrice.multiply(
                BigDecimal.valueOf(
                        quantity));
    }
}