package com.example.demo.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 商品情報を保持するモデルクラスです。
 *
 * PostgreSQLのproductsテーブルから取得した
 * 商品1件分のデータを格納します。
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    
    private Long id;            //商品ID
    private String description; //商品説明
    private String imageUrl;    //商品画像やURLのパス
    private String name;        //商品名
    private BigDecimal price;   //商品価格
    
    private Integer stock;      //在庫数

}