package com.example.demo.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 注文者情報入力画面と購入内容確認画面の入力値を保持します。
 *
 * 入力内容を変更してもmembersテーブルは更新しません。
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private String customerName;
    private String postalCode;
    private String address;
    private String phone;
    private String email;
    private String paymentMethod;
    private String fulfillmentMethod;
    private String pickupDate;
    private String pickupTime;

    /**
     * 受取方法追加前のコンストラクタとの互換性を残します。
     */
    public CheckoutForm(
            String customerName,
            String postalCode,
            String address,
            String phone,
            String email,
            String paymentMethod) {

        this.customerName = customerName;
        this.postalCode = postalCode;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.paymentMethod = paymentMethod;
    }
}
