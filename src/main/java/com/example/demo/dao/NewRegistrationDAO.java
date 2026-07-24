//package com.example.demo.dao;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//
//import org.springframework.stereotype.Repository;
//
//import com.example.demo.model.Member;
//
//@Repository
//public class NewRegistrationDAO {
//
//    private static final String JDBC_URL =
//            "jdbc:postgresql://localhost:5432/groupb_project";
//
//    private static final String DB_USER = "postgres";
//    private static final String DB_PASS = "psql";
//
//    public boolean create(Member member) {
//
//        try (Connection conn =
//                DriverManager.getConnection(
//                        JDBC_URL,
//                        DB_USER,
//                        DB_PASS)) {
//
//            String sql =
//                "INSERT INTO members "
//              + "(member_id, password, member_name, postal_code, "
//              + "address, phone_number, birth_date, email, payment_method) "
//              + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
//
//            PreparedStatement pStmt =
//                    conn.prepareStatement(sql);
//
//            pStmt.setString(1, member.getMemberId());
//            pStmt.setString(2, member.getPassword());
//            pStmt.setString(3, member.getMemberName());
//            pStmt.setString(4, member.getPostalCode());
//            pStmt.setString(5, member.getAddress());
//            pStmt.setString(6, member.getPhoneNumber());
//            pStmt.setString(7, member.getBirthDate());
//            pStmt.setString(8, member.getEmail());
//            pStmt.setString(9, member.getPaymentMethod());
//
//            int result = pStmt.executeUpdate();
//
//            return result == 1;
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//}