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

    /*
     * PostgreSQL接続情報
     */
    private static final String JDBC_URL =
            "jdbc:postgresql://localhost:5432/groupb_project";

    private static final String DB_USER =
            "postgres";

    private static final String DB_PASS =
            "psql";


    /**
     * 予約情報を登録する
     *
     * @param order 予約情報
     * @return 登録成功：true
     *         登録失敗：false
     */
    public boolean insert(Order order) {

        String sql =
                "INSERT INTO orders "
              + "(customer_name, email, phone, product_id, "
              + "reservation_date, reservation_time) "
              + "VALUES (?, ?, ?, ?, ?, ?)";

        try (
            Connection conn =
                    DriverManager.getConnection(
                            JDBC_URL,
                            DB_USER,
                            DB_PASS);

            PreparedStatement ps =
                    conn.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    order.getCustomerName());

            ps.setString(
                    2,
                    order.getEmail());

            ps.setString(
                    3,
                    order.getPhone());

            ps.setLong(
                    4,
                    order.getProductId());

            ps.setDate(
                    5,
                    java.sql.Date.valueOf(
                            order.getReservationDate()));

            ps.setTime(
                    6,
                    java.sql.Time.valueOf(
                            order.getReservationTime()));

            int result =
                    ps.executeUpdate();

            return result == 1;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }


    /**
     * 予約情報をすべて取得する
     *
     * @return 予約一覧
     */
    public List<Order> findAll() {

        List<Order> orderList =
                new ArrayList<>();

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

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery()
        ) {

            while (rs.next()) {

                Order order =
                        new Order();

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

                if (rs.getDate("reservation_date") != null) {

                    order.setReservationDate(
                            rs.getDate("reservation_date")
                              .toLocalDate());
                }

                if (rs.getTime("reservation_time") != null) {

                    order.setReservationTime(
                            rs.getTime("reservation_time")
                              .toLocalTime());
                }

                if (rs.getTimestamp("created_at") != null) {

                    order.setCreatedAt(
                            rs.getTimestamp("created_at")
                              .toLocalDateTime());
                }

                orderList.add(order);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return orderList;
    }
}