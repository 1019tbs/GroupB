package com.example.demo.service;

import java.util.List;

import com.example.demo.model.CartItem;
import com.example.demo.model.ShoppingOrder;

/**
 * 注文完了メール送信の共通インターフェースです。
 */
public interface OrderMailService {

    /**
     * @return 実際にメールを送信できた場合はtrue
     */
    boolean sendOrderConfirmation(
            long shoppingOrderId,
            ShoppingOrder order,
            List<CartItem> cartItems);
}