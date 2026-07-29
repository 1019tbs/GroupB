package com.example.demo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.demo.model.Member;

/**
 * 注文者情報の初期表示に使用する会員情報を取得します。
 *
 * ログインセッションには会員ID・氏名・権限しか
 * 入っていない場合があるため、membersテーブルから
 * 住所などを読み直します。
 */
@Repository
public class CheckoutMemberDAO {

    private static final String JDBC_URL =
            "jdbc:postgresql://localhost:5432/groupb_project";

    private static final String DB_USER =
            "postgres";

    private static final String DB_PASS =
            "psql";

    private static final String SELECT_BY_ID =
            "SELECT "
          + "member_id, "
          + "member_name, "
          + "postal_code, "
          + "address, "
          + "phone_number, "
          + "email, "
          + "payment_method "
          + "FROM members "
          + "WHERE member_id = ?";

    public Optional<Member> findById(
            String memberId) {

        try (
            Connection conn =
                    DriverManager.getConnection(
                            JDBC_URL,
                            DB_USER,
                            DB_PASS);

            PreparedStatement ps =
                    conn.prepareStatement(
                            SELECT_BY_ID)
        ) {

            ps.setString(1, memberId);

            try (ResultSet rs = ps.executeQuery()) {

                if (!rs.next()) {
                    return Optional.empty();
                }

                Member member = new Member();

                member.setMemberId(
                        rs.getString("member_id"));

                member.setMemberName(
                        rs.getString("member_name"));

                member.setPostalCode(
                        rs.getString("postal_code"));

                member.setAddress(
                        rs.getString("address"));

                member.setPhoneNumber(
                        rs.getString("phone_number"));

                member.setEmail(
                        rs.getString("email"));

                member.setPaymentMethod(
                        rs.getString("payment_method"));

                return Optional.of(member);
            }

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "会員情報の取得に失敗しました。",
                    e);
        }
    }
}