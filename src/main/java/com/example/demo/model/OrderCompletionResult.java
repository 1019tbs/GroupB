package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 注文確定結果をControllerへ返します。
 */
@Getter
@AllArgsConstructor
public class OrderCompletionResult {

    private final long shoppingOrderId;
    private final boolean mailSent;
}