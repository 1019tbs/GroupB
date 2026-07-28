package com.example.demo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.model.CartItem_oonaka;
import com.example.demo.model.Product_oonaka;



public class CartDAO {

    private static final String JDBC_URL =
            "jdbc:postgresql://localhost:5432/groupb_project";

    private static final String DB_USER =
            "postgres";

    private static final String DB_PASS =
            "psql";

    public Long findCartIdByMemberId(String memberId) {

        String sql =
                "SELECT cart_id FROM carts WHERE member_id = ?";

        try (
            Connection conn =
                    DriverManager.getConnection(
                            JDBC_URL,
                            DB_USER,
                            DB_PASS
                    );

            PreparedStatement ps =
                    conn.prepareStatement(sql)
        ) {

            ps.setString(1, memberId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getLong("cart_id");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    public Long createCart(String memberId) {

        String sql =
                "INSERT INTO carts (member_id) "
              + "VALUES (?) "
              + "RETURNING cart_id";

        try (
            Connection conn =
                    DriverManager.getConnection(
                            JDBC_URL,
                            DB_USER,
                            DB_PASS
                    );

            PreparedStatement ps =
                    conn.prepareStatement(sql)
        ) {

            ps.setString(1, memberId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getLong("cart_id");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    public void addProductToCart(Long cartId, Long productId, int quantity) {

        String sql =
                "INSERT INTO cart_items (cart_id, product_id, quantity) "
              + "VALUES (?, ?, ?) "
              + "ON CONFLICT (cart_id, product_id) "
              + "DO UPDATE SET "
              + "quantity = cart_items.quantity + EXCLUDED.quantity, "
              + "updated_at = CURRENT_TIMESTAMP";

        try (
            Connection conn =
                    DriverManager.getConnection(
                            JDBC_URL,
                            DB_USER,
                            DB_PASS
                    );

            PreparedStatement ps =
                    conn.prepareStatement(sql)
        ) {

            ps.setLong(1, cartId);
            ps.setLong(2, productId);
            ps.setInt(3, quantity);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<CartItem_oonaka> findCartItems(String memberId) {

        List<CartItem_oonaka> cartList =
                new ArrayList<>();

        String sql =
                "SELECT "
              + "p.product_id, "
              + "p.product_name, "
              + "p.price, "
              + "p.stock, "
              + "p.category_id, "
              + "p.description, "
              + "p.image_url, "
              + "p.created_at, "
              + "p.updated_at, "
              + "ci.quantity "
              + "FROM carts c "
              + "JOIN cart_items ci "
              + "ON c.cart_id = ci.cart_id "
              + "JOIN products p "
              + "ON ci.product_id = p.product_id "
              + "WHERE c.member_id = ? "
              + "ORDER BY ci.cart_item_id";

        try (
            Connection conn =
                    DriverManager.getConnection(
                            JDBC_URL,
                            DB_USER,
                            DB_PASS
                    );

            PreparedStatement ps =
                    conn.prepareStatement(sql)
        ) {

            ps.setString(1, memberId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Product_oonaka product =
                            new Product_oonaka();

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
                            rs.getTimestamp("created_at")
                                    .toLocalDateTime());

                    product.setUpdatedAt(
                            rs.getTimestamp("updated_at")
                                    .toLocalDateTime());

                    CartItem_oonaka cartItem =
                            new CartItem_oonaka(
                                    product,
                                    rs.getInt("quantity")
                            );

                    cartList.add(cartItem);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cartList;
    }
}