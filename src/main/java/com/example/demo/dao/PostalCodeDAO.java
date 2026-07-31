package com.example.demo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.example.demo.model.PostalCodeSearch;

@Repository
public class PostalCodeDAO {
	// DB接続情報（プロジェクト内で共通の設定）
	private static final String JDBC_URL = 
		"jdbc:postgresql://localhost:5432/groupb_project";
    private static final String DB_USER = "postgres";
    private static final String DB_PASS = "psql";

    // 郵便番号から住所を取得するメソッド
    public PostalCodeSearch findByPostalCode(String postalCode) {
    	if (postalCode == null || postalCode.isEmpty()) {
            return null;
        }
    	
    	try {
    		Class.forName("org.postgresql.Driver");
    	} catch (ClassNotFoundException e) {
    		e.printStackTrace();
    	}
    	String sql = "SELECT * FROM postal_code_search "
    		+ "WHERE REPLACE(postal_code, '-', '') = REPLACE(?, '-', '')";

    	// try(...) の中で Connection や PreparedStatement を生成することで、自動的に close されます
    	try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
    		PreparedStatement pStmt = conn.prepareStatement(sql)) {

            pStmt.setString(1, postalCode);

            try (ResultSet rs = pStmt.executeQuery()) {
                if (rs.next()) {
                    PostalCodeSearch result = new PostalCodeSearch();
                    result.setPostalCode(rs.getString("postal_code"));
                    result.setPrefecture(rs.getString("prefecture"));
                    result.setCity(rs.getString("city"));
                    result.setTown(rs.getString("town"));
                    return result;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // 見つからない場合
    }
}