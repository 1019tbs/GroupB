package com.example.demo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.model.FormContact;

@Repository
public class FormContactDAO {

	/*
	 * PostgreSQL接続情報
	 */
	private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/groupb_project";

	private static final String DB_USER = "postgres";

	private static final String DB_PASS = "psql";

	/**
	 * お問い合わせ内容を登録する
	 *
	 * @param contact お問い合わせ情報
	 * @return 登録成功：true、登録失敗：false
	 */
	public boolean insert(
			FormContact contact) {

		String sql = "INSERT INTO form_contact "
				+ "(customer_name, subject, email, phone, message) "
				+ "VALUES (?, ?, ?, ?, ?)";

		try (
				Connection conn = DriverManager.getConnection(
						JDBC_URL,
						DB_USER,
						DB_PASS);

				PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(
					1,
					contact.getCustomerName());

			ps.setString(
					2,
					contact.getSubject());

			ps.setString(
					3,
					contact.getEmail());

			ps.setString(
					4,
					contact.getPhone());

			ps.setString(
					5,
					contact.getMessage());

			int result = ps.executeUpdate();

			return result == 1;

		} catch (Exception e) {

			e.printStackTrace();

			return false;
		}
	}

	/**
	 * お問い合わせ内容をすべて取得する
	 *
	 * @return お問い合わせ一覧
	 */
	public List<FormContact> findAll() {

		List<FormContact> contactList = new ArrayList<>();

		String sql = "SELECT "
				+ "contact_id, "
				+ "customer_name, "
				+ "subject, "
				+ "email, "
				+ "phone, "
				+ "message, "
				+ "created_at, "
				+ "status "
				+ "FROM form_contact "
				+ "ORDER BY created_at DESC";

		try (
				Connection conn = DriverManager.getConnection(
						JDBC_URL,
						DB_USER,
						DB_PASS);

				PreparedStatement ps = conn.prepareStatement(sql);

				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {

				FormContact contact = new FormContact();

				contact.setContactId(
						rs.getLong("contact_id"));

				contact.setCustomerName(
						rs.getString("customer_name"));

				contact.setSubject(
						rs.getString("subject"));

				contact.setEmail(
						rs.getString("email"));

				contact.setPhone(
						rs.getString("phone"));

				contact.setMessage(
						rs.getString("message"));

				Timestamp createdAt = rs.getTimestamp("created_at");

				if (createdAt != null) {

					contact.setCreatedAt(
							createdAt.toLocalDateTime());
				}

				contact.setStatus(
						rs.getInt("status"));

				contactList.add(contact);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return contactList;
	}

	/**
	 * お問い合わせの対応状況を更新する
	 *
	 * @param contactId お問い合わせID
	 * @param status 対応状況（0：未対応、1：対応済み）
	 * @return 更新成功：true、失敗：false
	 */
	public boolean updateStatus(
			long contactId,
			int status) {

		String sql = "UPDATE form_contact "
				+ "SET status = ? "
				+ "WHERE contact_id = ?";

		try (
				Connection conn = DriverManager.getConnection(
						JDBC_URL,
						DB_USER,
						DB_PASS);

				PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(
					1,
					status);

			ps.setLong(
					2,
					contactId);

			int result = ps.executeUpdate();

			return result == 1;

		} catch (Exception e) {

			e.printStackTrace();

			return false;
		}
	}

	/**
	 * お問い合わせを削除する
	 *
	 * @param contactId お問い合わせID
	 * @return 削除成功：true、失敗：false
	 */
	public boolean delete(
			long contactId) {

		String sql = "DELETE FROM form_contact "
				+ "WHERE contact_id = ?";

		try (
				Connection conn = DriverManager.getConnection(
						JDBC_URL,
						DB_USER,
						DB_PASS);

				PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setLong(
					1,
					contactId);

			int result = ps.executeUpdate();

			return result == 1;

		} catch (Exception e) {

			e.printStackTrace();

			return false;
		}
	}

	/*
	 * お問い合わせIDを指定して１件取得する
	 * @param contactId　お問い合わせID
	 * @retun　お問い合わせ情報
	 */
	public FormContact findById(
			long contactId) {

		String sql = "SELECT "
				+ "contact_id, "
				+ "customer_name, "
				+ "subject, "
				+ "email, "
				+ "phone, "
				+ "message, "
				+ "created_at, "
				+ "status "
				+ "FROM form_contact "
				+ "WHERE contact_id = ?";

		try (
				Connection conn = DriverManager.getConnection(
						JDBC_URL,
						DB_USER,
						DB_PASS);

				PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setLong(1, contactId);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					FormContact contact = new FormContact();
					contact.setContactId(
							rs.getLong("contact_id"));

					contact.setCustomerName(
							rs.getString("customer_name"));

					contact.setSubject(
							rs.getString("subject"));

					contact.setEmail(
							rs.getString("email"));

					contact.setPhone(
							rs.getString("phone"));

					contact.setMessage(
							rs.getString("message"));

					Timestamp createdAt = rs.getTimestamp("created_at");

					if (createdAt != null) {
						contact.setCreatedAt(createdAt.toLocalDateTime());
					}
					contact.setStatus(rs.getInt("status"));
					return contact;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}