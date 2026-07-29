package com.example.demo.service;

import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import com.example.demo.model.CartItem_oonaka;
import com.example.demo.model.ShoppingOrder;

/**
 * メール機能が無効な開発環境用です。
 */
@Service
@ConditionalOnProperty(
        name = "app.mail.enabled",
        havingValue = "false",
        matchIfMissing = true)
public class NoOpOrderMailService
        implements OrderMailService {

    @Override
    public boolean sendOrderConfirmation(
            long shoppingOrderId,
            ShoppingOrder order,
            List<CartItem_oonaka> cartItems) {

        System.out.println(
                "【メール未送信】"
                + "app.mail.enabled=false "
                + "注文ID="
                + shoppingOrderId);

        return false;
    }
}