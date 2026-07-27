package com.example.demo.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.example.demo.model.Member;

/**
 * membersテーブルへの登録処理を担当するDAOです。
 *
 * 主な処理
 * ・会員IDがすでに登録されているか確認する
 * ・新しい会員をmembersテーブルへ登録する
 */
@Repository
public class NewRegistrationDAO {

    /*
     * PostgreSQLへの接続情報です。
     */
    private static final String JDBC_URL =
            "jdbc:postgresql://localhost:5432/groupb_project";

    private static final String DB_USER = "postgres";
    private static final String DB_PASS = "psql";

    /*
     * 会員IDが登録済みか確認するSQLです。
     */
    private static final String EXISTS_BY_MEMBER_ID =
            "SELECT 1 " +
            "FROM members " +
            "WHERE member_id = ?";

    /*
     * 会員を登録するSQLです。
     *
     * Memberクラスにある10項目を登録します。
     */
    private static final String INSERT_MEMBER =
            "INSERT INTO members (" +
            "member_id, " +
            "password, " +
            "member_name, " +
            "postal_code, " +
            "address, " +
            "phone_number, " +
            "birth_date, " +
            "email, " +
            "payment_method, " +
            "role" +
            ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    /**
     * 指定された会員IDが登録済みか確認します。
     *
     * @param memberId 会員ID
     * @return 登録済みの場合はtrue
     */
    public boolean existsByMemberId(String memberId) {

        try (
            Connection conn = getConnection();
            PreparedStatement ps =
                    conn.prepareStatement(EXISTS_BY_MEMBER_ID)
        ) {
            ps.setString(1, memberId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "会員IDの確認中にデータベースエラーが発生しました。",
                    e);
        }
    }

    /**
     * 新しい会員をmembersテーブルへ登録します。
     *
     * @param member 登録する会員情報
     * @return 1件登録できた場合はtrue
     */
    public boolean create(Member member) {

        try (
            Connection conn = getConnection();
            PreparedStatement ps =
                    conn.prepareStatement(INSERT_MEMBER)
        ) {
            /*
             * SQLの「?」へ順番に値を設定します。
             */
            ps.setString(1, member.getMemberId());
            ps.setString(2, member.getPassword());
            ps.setString(3, member.getMemberName());
            ps.setString(4, member.getPostalCode());
            ps.setString(5, member.getAddress());
            ps.setString(6, member.getPhoneNumber());

            /*
             * MemberのbirthDateはLocalDate型です。
             *
             * java.sql.Dateへ変換して、
             * PostgreSQLのDATE型へ登録します。
             */
            ps.setDate(
                    7,
                    Date.valueOf(member.getBirthDate()));

            ps.setString(8, member.getEmail());
            ps.setString(9, member.getPaymentMethod());
            ps.setString(10, member.getRole());

            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "会員登録中にデータベースエラーが発生しました。",
                    e);
        }
    }

    /**
     * PostgreSQLへ接続します。
     *
     * @return DB接続
     * @throws SQLException 接続に失敗した場合
     */
    private Connection getConnection() throws SQLException {

        return DriverManager.getConnection(
                JDBC_URL,
                DB_USER,
                DB_PASS);
    }
}