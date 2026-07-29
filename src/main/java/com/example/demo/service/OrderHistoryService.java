package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dao.ShoppingOrderDAO;
import com.example.demo.dao.ShoppingOrderItemDAO;
import com.example.demo.model.ShoppingOrder;

import lombok.RequiredArgsConstructor;

/**
 * ログイン会員の注文履歴を取得します。
 */
@Service
@RequiredArgsConstructor
public class OrderHistoryService {

    private final ShoppingOrderDAO
            shoppingOrderDAO;

    private final ShoppingOrderItemDAO
            shoppingOrderItemDAO;

    public List<ShoppingOrder> findHistory(
            String memberId) {

        validateMemberId(memberId);

        List<ShoppingOrder> orderList =
                shoppingOrderDAO
                        .findByMemberId(
                                memberId);

        for (ShoppingOrder order
                : orderList) {

            order.setItems(
                    shoppingOrderItemDAO
                            .findByOrderId(
                                    order.getShoppingOrderId()));
        }

        return orderList;
    }

    public ShoppingOrder findDetail(
            String memberId,
            long shoppingOrderId) {

        validateMemberId(memberId);

        if (shoppingOrderId <= 0) {
            throw new IllegalArgumentException(
                    "注文IDが正しくありません。");
        }

        ShoppingOrder order =
                shoppingOrderDAO
                        .findByIdAndMemberId(
                                shoppingOrderId,
                                memberId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "注文情報が見つかりません。"));

        order.setItems(
                shoppingOrderItemDAO
                        .findByOrderId(
                                shoppingOrderId));

        return order;
    }

    private void validateMemberId(
            String memberId) {

        if (memberId == null
                || memberId.isBlank()) {

            throw new IllegalArgumentException(
                    "会員IDが指定されていません。");
        }
    }
}