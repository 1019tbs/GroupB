package com.example.demo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.stereotype.Repository;

import com.example.demo.model.Member;

@Repository
public class LoginListDAO {

    // PostgreSQL接続情報
    private static final String JDBC_URL =
            "jdbc:postgresql://localhost:5432/groupb_project";
    private static final String DB_USER = "postgres";
    private static final String DB_PASS = "psql";

    /**
     * ログイン認証
     * @param member 会員IDとパスワード
     * @return ログイン成功：Member
     *         ログイン失敗：null
     */
    public Member findByLogin(Member member) {

        String sql =
                "SELECT member_id, password, member_name, role " +
                "FROM members " +
                "WHERE member_id = ? " +
                "AND password = ?";

        try (
            Connection conn =
                    DriverManager.getConnection(
                            JDBC_URL,
                            DB_USER,
                            DB_PASS);

            PreparedStatement ps =
                    conn.prepareStatement(sql)
        ) {

            ps.setString(1, member.getMemberId());
            ps.setString(2, member.getPassword());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Member loginMember = new Member();

                loginMember.setMemberId(
                        rs.getString("member_id"));

                loginMember.setPassword(
                        rs.getString("password"));

                loginMember.setMemberName(
                        rs.getString("member_name"));

                loginMember.setRole(
                        rs.getString("role"));

                return loginMember;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}