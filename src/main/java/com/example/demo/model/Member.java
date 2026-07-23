package com.example.demo.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    private String memberId;        // 会員ID
    private String password;        // パスワード
    private String memberName;      // 氏名
    private String postalCode;      // 郵便番号
    private String address;         // 住所
    private String phoneNumber;     // 電話番号
    private LocalDate birthDate;    // 生年月日
    private String email;           // メールアドレス
    private String paymentMethod;   // 支払方法
    private String role;            // 権限
}