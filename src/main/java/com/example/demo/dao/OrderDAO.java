package com.example.demo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.model.Order;

@Repository
public class OrderDAO {

    private static final String JDBC_URL =
            "jdbc:postgresql://localhost:5432/groupb_project";

    private static final String DB_USER = "postgres";
    private static final String DB_PASS = "psql";

    /**
     * 予約一覧を取得
     */
    public List<Order> findAll() {

        List<Order> orderList = new ArrayList<>();

        String sql =
                "SELECT "
              + "o.order_id, "
              + "o.customer_name, "
              + "o.email, "
              + "o.phone, "
              + "o.product_id, "
              + "p.name AS product_name, "
              + "o.reservation_date, "
              + "o.reservation_time, "
              + "o.created_at "
              + "FROM orders o "
              + "JOIN products p "
              + "ON o.product_id = p.id "
              + "ORDER BY o.order_id DESC";

        try (
            Connection conn =
                    DriverManager.getConnection(
                            JDBC_URL,
                            DB_USER,
                            DB_PASS);

            PreparedStatement pStmt =
                    conn.prepareStatement(sql);

            ResultSet rs =
                    pStmt.executeQuery()
        ) {

            while (rs.next()) {

                Order order = new Order();

                order.setOrderId(
                        rs.getLong("order_id"));

                order.setCustomerName(
                        rs.getString("customer_name"));

                order.setEmail(
                        rs.getString("email"));

                order.setPhone(
                        rs.getString("phone"));

                order.setProductId(
                        rs.getLong("product_id"));

                order.setProductName(
                        rs.getString("product_name"));

                order.setReservationDate(
                        rs.getDate("reservation_date")
                          .toLocalDate());

                order.setReservationTime(
                        rs.getTime("reservation_time")
                          .toLocalTime());

                order.setCreatedAt(
                        rs.getTimestamp("created_at")
                          .toLocalDateTime());

                orderList.add(order);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderList;
    }
}