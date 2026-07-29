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
}