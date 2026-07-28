package com.example.demo.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * productsテーブルの商品1件分を保持するモデルクラスです。
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

    /** カテゴリーID（1:CAKES、2:BAKES、3:PASTRIES） */
    private Integer categoryId;

    /** 商品説明 */
    private String description;

    /** 商品画像のURLまたはファイルパス */
    private String imageUrl;

    /** 取扱状態（true:取扱中、false:取扱停止） */
    private Boolean active = true;

    /** 商品登録日時 */
    private LocalDateTime createdAt;

    /** 商品更新日時 */
    private LocalDateTime updatedAt;

    /**
     * active列追加前のコードとの互換性を残すためのコンストラクタです。
     */
    public Product(
            Long productId,
            String productName,
            BigDecimal price,
            Integer stock,
            Integer categoryId,
            String description,
            String imageUrl,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {

        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.stock = stock;
        this.categoryId = categoryId;
        this.description = description;
        this.imageUrl = imageUrl;
        this.active = true;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}