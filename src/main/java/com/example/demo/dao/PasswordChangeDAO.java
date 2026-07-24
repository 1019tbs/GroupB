package com.example.demo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.stereotype.Repository;

@Repository
public class PasswordChangeDAO {

    private static final String JDBC_URL =
            "jdbc:postgresql://localhost:5432/groupb_project";

    private static final String DB_USER =
            "postgres";

    private static final String DB_PASS =
            "psql";

    /*
     * 現在のパスワードが正しいか確認する
     */
    public boolean checkPassword(
            String memberId,
            String currentPassword) {

        String sql =
                "SELECT member_id "
              + "FROM members "
              + "WHERE member_id = ? "
              + "AND password = ?";

        try (
            Connection conn =
                    DriverManager.getConnection(
                            JDBC_URL,
                            DB_USER,
                            DB_PASS);

            PreparedStatement ps =
                    conn.prepareStatement(sql)
        ) {

            ps.setString(1, memberId);
            ps.setString(2, currentPassword);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /*
     * パスワードだけを更新する
     */
    public boolean updatePassword(
            String memberId,
            String newPassword) {

        String sql =
                "UPDATE members "
              + "SET password = ?, "
              + "updated_at = CURRENT_TIMESTAMP "
              + "WHERE member_id = ?";

        try (
            Connection conn =
                    DriverManager.getConnection(
                            JDBC_URL,
                            DB_USER,
                            DB_PASS);

            PreparedStatement ps =
                    conn.prepareStatement(sql)
        ) {

            ps.setString(1, newPassword);
            ps.setString(2, memberId);

            int result =
                    ps.executeUpdate();

            return result == 1;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}