package com.example.demo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.example.demo.model.Member;

/**
 * membersテーブルを使ってログイン認証を行います。
 *
 * main.jspで氏名を表示し、管理者画面でroleを判定できるように、
 * member_nameとroleも必ず取得します。
 */
@Repository
public class LoginListDAO {

    private static final String JDBC_URL =
            "jdbc:postgresql://localhost:5432/groupb_project";

    private static final String DB_USER = "postgres";
    private static final String DB_PASS = "psql";

    private static final String SELECT_BY_LOGIN =
            "SELECT " +
            "member_id, " +
            "password, " +
            "member_name, " +
            "role " +
            "FROM members " +
            "WHERE member_id = ? " +
            "AND password = ?";

    /**
     * 会員IDとパスワードが一致する会員を取得します。
     *
     * @param member 入力された会員ID・パスワード
     * @return 一致したMember。見つからない場合はnull
     */
    public Member findByLogin(Member member) {

        try (
            Connection conn = DriverManager.getConnection(
                    JDBC_URL,
                    DB_USER,
                    DB_PASS);
            PreparedStatement ps = conn.prepareStatement(SELECT_BY_LOGIN)
        ) {
            ps.setString(1, member.getMemberId());
            ps.setString(2, member.getPassword());

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }

                Member loginUser = new Member();
                loginUser.setMemberId(rs.getString("member_id"));
                loginUser.setPassword(rs.getString("password"));
                loginUser.setMemberName(rs.getString("member_name"));
                loginUser.setRole(rs.getString("role"));

                return loginUser;
            }

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "ログイン認証中にデータベースエラーが発生しました。",
                    e);
        }
    }
}