package com.example.demo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.model.Product_cart;

@Repository
public class ProductDAO_oonaka {

    private static final String JDBC_URL =
            "jdbc:postgresql://localhost:5432/groupb_project";

    private static final String DB_USER =
            "postgres";

    private static final String DB_PASS =
            "psql";

    /*
     * 商品一覧を取得する
     */
    public List<Product_cart> findAll() {

        List<Product_cart> productList = new ArrayList<>();

        String sql =
                "SELECT * FROM products ORDER BY product_id";

        try (
            Connection conn =
                    DriverManager.getConnection(
                            JDBC_URL,
                            DB_USER,
                            DB_PASS);

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery()
        ) {

            while (rs.next()) {

                Product_cart product =
                        new Product_cart();

                product.setProductId(
                        rs.getLong("product_id"));

                product.setProductName(
                        rs.getString("product_name"));

                product.setPrice(
                        rs.getBigDecimal("price"));

                product.setStock(
                        rs.getInt("stock"));

                product.setCategoryId(
                        rs.getInt("category_id"));

                product.setDescription(
                        rs.getString("description"));

                product.setImageUrl(
                        rs.getString("image_url"));

                product.setCreatedAt(
                        rs.getTimestamp("created_at").toLocalDateTime());

                product.setUpdatedAt(
                        rs.getTimestamp("updated_at").toLocalDateTime());

                productList.add(product);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return productList;
    }
}