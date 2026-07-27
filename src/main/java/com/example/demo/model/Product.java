package com.example.demo.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * productsテーブルの商品1件分を保持するモデルクラスです。
 *
 * DBの列名とJavaのフィールド名を次のように対応させています。
 *
 * product_id   → productId
 * product_name → productName
 * category_id  → categoryId
 * image_url    → imageUrl
 * created_at   → createdAt
 * updated_at   → updatedAt
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    /** 商品ID */
    private Long productId;

    /** 商品名 */
    private String productName;

    /** 商品価格 */
    private BigDecimal price;

    /** 在庫数 */
    private Integer stock;

    /** カテゴリーID */
    private Integer categoryId;

    /** 商品説明 */
    private String description;

    /** 商品画像のURLまたはファイルパス */
    private String imageUrl;

    /** 商品登録日時 */
    private LocalDateTime createdAt;

    /** 商品更新日時 */
    private LocalDateTime updatedAt;
}