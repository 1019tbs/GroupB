package com.example.demo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.demo.model.ShoppingOrder;

/**
 * shopping_ordersテーブルへの登録・検索を担当します。
 */
@Repository
public class ShoppingOrderDAO {

    private static final String JDBC_URL =
            "jdbc:postgresql://localhost:5432/groupb_project";

    private static final String DB_USER =
            "postgres";

    private static final String DB_PASS =
            "psql";

    private static final String ORDER_COLUMNS =
            "shopping_order_id, "
          + "member_id, "
          + "customer_name, "
          + "postal_code, "
          + "address, "
          + "phone, "
          + "email, "
          + "payment_method, "
          + "total_amount, "
          + "order_status, "
          + "checkout_token, "
          + "created_at ";

    private static final String INSERT_ORDER =
            "INSERT INTO shopping_orders ("
          + "member_id, "
          + "customer_name, "
          + "postal_code, "
          + "address, "
          + "phone, "
          + "email, "
          + "payment_method, "
          + "total_amount, "
          + "order_status, "
          + "checkout_token"
          + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) "
          + "RETURNING shopping_order_id";

    private static final String
            SELECT_BY_MEMBER_ID =
                    "SELECT "
                  + ORDER_COLUMNS
                  + "FROM shopping_orders "
                  + "WHERE member_id = ? "
                  + "ORDER BY created_at DESC, "
                  + "shopping_order_id DESC";

    private static final String
            SELECT_BY_ID_AND_MEMBER_ID =
                    "SELECT "
                  + ORDER_COLUMNS
                  + "FROM shopping_orders "
                  + "WHERE shopping_order_id = ? "
                  + "AND member_id = ?";

    /**
     * 注文本体を登録し、発行された注文IDを返します。
     *
     * commit・rollback・closeはCheckoutServiceが担当します。
     */
    public long insert(
            Connection conn,
            ShoppingOrder order)
            throws SQLException {

        if (conn == null) {
            throw new IllegalArgumentException(
                    "DB接続が指定されていません。");
        }

        if (order == null) {
            throw new IllegalArgumentException(
                    "注文情報が指定されていません。");
        }

        try (PreparedStatement ps =
                conn.prepareStatement(
                        INSERT_ORDER)) {

            ps.setString(
                    1,
                    order.getMemberId());

            ps.setString(
                    2,
                    order.getCustomerName());

            ps.setString(
                    3,
                    order.getPostalCode());

            ps.setString(
                    4,
                    order.getAddress());

            ps.setString(
                    5,
                    order.getPhone());

            ps.setString(
                    6,
                    order.getEmail());

            ps.setString(
                    7,
                    order.getPaymentMethod());

            ps.setBigDecimal(
                    8,
                    order.getTotalAmount());

            ps.setString(
                    9,
                    order.getOrderStatus());

            ps.setString(
                    10,
                    order.getCheckoutToken());

            try (ResultSet rs =
                    ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getLong(
                            "shopping_order_id");
                }
            }
        }

        throw new SQLException(
                "注文IDを取得できませんでした。");
    }

    /**
     * ログイン会員の注文履歴を取得します。
     */
    public List<ShoppingOrder> findByMemberId(
            String memberId) {

        List<ShoppingOrder> orderList =
                new ArrayList<>();

        try (
            Connection conn =
                    getConnection();

            PreparedStatement ps =
                    conn.prepareStatement(
                            SELECT_BY_MEMBER_ID)
        ) {

            ps.setString(1, memberId);

            try (ResultSet rs =
                    ps.executeQuery()) {

                while (rs.next()) {
                    orderList.add(
                            mapRow(rs));
                }
            }

            return orderList;

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "注文履歴の取得に失敗しました。",
                    e);
        }
    }

    /**
     * 注文IDと会員IDの両方を条件に取得します。
     *
     * 他会員の注文詳細を表示しないための検索です。
     */
    public Optional<ShoppingOrder>
            findByIdAndMemberId(
                    long shoppingOrderId,
                    String memberId) {

        try (
            Connection conn =
                    getConnection();

            PreparedStatement ps =
                    conn.prepareStatement(
                            SELECT_BY_ID_AND_MEMBER_ID)
        ) {

            ps.setLong(
                    1,
                    shoppingOrderId);

            ps.setString(
                    2,
                    memberId);

            try (ResultSet rs =
                    ps.executeQuery()) {

                if (rs.next()) {
                    return Optional.of(
                            mapRow(rs));
                }
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "注文情報の取得に失敗しました。",
                    e);
        }
    }

    private ShoppingOrder mapRow(
            ResultSet rs)
            throws SQLException {

        ShoppingOrder order =
                new ShoppingOrder();

        order.setShoppingOrderId(
                rs.getLong(
                        "shopping_order_id"));

        order.setMemberId(
                rs.getString(
                        "member_id"));

        order.setCustomerName(
                rs.getString(
                        "customer_name"));

        order.setPostalCode(
                rs.getString(
                        "postal_code"));

        order.setAddress(
                rs.getString(
                        "address"));

        order.setPhone(
                rs.getString(
                        "phone"));

        order.setEmail(
                rs.getString(
                        "email"));

        order.setPaymentMethod(
                rs.getString(
                        "payment_method"));

        order.setTotalAmount(
                rs.getBigDecimal(
                        "total_amount"));

        order.setOrderStatus(
                rs.getString(
                        "order_status"));

        order.setCheckoutToken(
                rs.getString(
                        "checkout_token"));

        if (rs.getTimestamp(
                "created_at") != null) {

            order.setCreatedAt(
                    rs.getTimestamp(
                            "created_at")
                            .toLocalDateTime());
        }

        return order;
    }

    private Connection getConnection()
            throws SQLException {

        return DriverManager.getConnection(
                JDBC_URL,
                DB_USER,
                DB_PASS);
    }
}