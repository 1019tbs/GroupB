package com.example.demo.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.stereotype.Repository;

import com.example.demo.model.Member;

@Repository
public class MemberEditDAO {

    private static final String JDBC_URL =
            "jdbc:postgresql://localhost:5432/groupb_project";

    private static final String DB_USER =
            "postgres";

    private static final String DB_PASS =
            "psql";

    /*
     * 会員IDを使って会員情報を1件取得する
     * パスワードは会員情報変更画面では使用しないため取得しない
     */
    public Member findById(String memberId) {

        String sql =
                "SELECT "
              + "member_id, "
              + "member_name, "
              + "postal_code, "
              + "address, "
              + "phone_number, "
              + "birth_date, "
              + "email, "
              + "payment_method, "
              + "role "
              + "FROM members "
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

            ps.setString(
                    1,
                    memberId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Member member =
                            new Member();

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

                    Date birthDate =
                            rs.getDate("birth_date");

                    if (birthDate != null) {
                        member.setBirthDate(
                                birthDate.toLocalDate());
                    }

                    member.setEmail(
                            rs.getString("email"));

                    member.setPaymentMethod(
                            rs.getString("payment_method"));

                    member.setRole(
                            rs.getString("role"));

                    return member;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /*
     * 会員情報を更新する
     * パスワードは別画面で変更するため更新対象外
     */
    public boolean update(Member member) {

        String sql =
                "UPDATE members "
              + "SET "
              + "member_name = ?, "
              + "postal_code = ?, "
              + "address = ?, "
              + "phone_number = ?, "
              + "birth_date = ?, "
              + "email = ?, "
              + "payment_method = ?, "
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

            ps.setString(
                    1,
                    member.getMemberName());

            ps.setString(
                    2,
                    member.getPostalCode());

            ps.setString(
                    3,
                    member.getAddress());

            ps.setString(
                    4,
                    member.getPhoneNumber());

            ps.setDate(
                    5,
                    Date.valueOf(
                            member.getBirthDate()));

            ps.setString(
                    6,
                    member.getEmail());

            ps.setString(
                    7,
                    member.getPaymentMethod());

            ps.setString(
                    8,
                    member.getMemberId());

            int result =
                    ps.executeUpdate();

            return result == 1;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}