package com.example.demo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.model.ShoppingOrderItem;

/**
 * shopping_order_itemsテーブルへの登録・検索を担当します。
 */
@Repository
public class ShoppingOrderItemDAO {

    private static final String JDBC_URL =
            "jdbc:postgresql://localhost:5432/groupb_project";

    private static final String DB_USER =
            "postgres";

    private static final String DB_PASS =
            "psql";

    private static final String INSERT_ITEM =
            "INSERT INTO shopping_order_items ("
          + "shopping_order_id, "
          + "product_id, "
          + "product_name, "
          + "unit_price, "
          + "quantity"
          + ") VALUES (?, ?, ?, ?, ?)";

    private static final String
            SELECT_BY_ORDER_ID =
                    "SELECT "
                  + "shopping_order_item_id, "
                  + "shopping_order_id, "
                  + "product_id, "
                  + "product_name, "
                  + "unit_price, "
                  + "quantity "
                  + "FROM shopping_order_items "
                  + "WHERE shopping_order_id = ? "
                  + "ORDER BY shopping_order_item_id";

    /**
     * 注文明細を登録します。
     *
     * commit・rollback・closeはCheckoutServiceが担当します。
     */
    public boolean insert(
            Connection conn,
            ShoppingOrderItem item)
            throws SQLException {

        if (conn == null) {
            throw new IllegalArgumentException(
                    "DB接続が指定されていません。");
        }

        if (item == null) {
            throw new IllegalArgumentException(
                    "注文明細が指定されていません。");
        }

        try (PreparedStatement ps =
                conn.prepareStatement(
                        INSERT_ITEM)) {

            ps.setLong(
                    1,
                    item.getShoppingOrderId());

            ps.setLong(
                    2,
                    item.getProductId());

            ps.setString(
                    3,
                    item.getProductName());

            ps.setBigDecimal(
                    4,
                    item.getUnitPrice());

            ps.setInt(
                    5,
                    item.getQuantity());

            return ps.executeUpdate() == 1;
        }
    }

    /**
     * 指定注文の全明細を取得します。
     */
    public List<ShoppingOrderItem> findByOrderId(
            long shoppingOrderId) {

        List<ShoppingOrderItem> itemList =
                new ArrayList<>();

        try (
            Connection conn =
                    getConnection();

            PreparedStatement ps =
                    conn.prepareStatement(
                            SELECT_BY_ORDER_ID)
        ) {

            ps.setLong(
                    1,
                    shoppingOrderId);

            try (ResultSet rs =
                    ps.executeQuery()) {

                while (rs.next()) {
                    itemList.add(
                            mapRow(rs));
                }
            }

            return itemList;

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "注文明細の取得に失敗しました。",
                    e);
        }
    }

    private ShoppingOrderItem mapRow(
            ResultSet rs)
            throws SQLException {

        ShoppingOrderItem item =
                new ShoppingOrderItem();

        item.setShoppingOrderItemId(
                rs.getLong(
                        "shopping_order_item_id"));

        item.setShoppingOrderId(
                rs.getLong(
                        "shopping_order_id"));

        item.setProductId(
                rs.getLong(
                        "product_id"));

        item.setProductName(
                rs.getString(
                        "product_name"));

        item.setUnitPrice(
                rs.getBigDecimal(
                        "unit_price"));

        item.setQuantity(
                rs.getInt(
                        "quantity"));

        return item;
    }

    private Connection getConnection()
            throws SQLException {

        return DriverManager.getConnection(
                JDBC_URL,
                DB_USER,
                DB_PASS);
    }
}