package com.example.demo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.demo.model.Product;

/**
 * productsテーブルへアクセスするDAOクラスです。
 *
 * 主な処理
 * ・商品をすべて取得する
 * ・商品IDで商品を1件取得する
 * ・在庫数を更新する
 * ・購入数量分だけ在庫を減らす
 */
@Repository
public class ProductDAO {

    /*
     * PostgreSQLへの接続情報です。
     */
    private static final String JDBC_URL =
            "jdbc:postgresql://localhost:5432/groupb_project";

    private static final String DB_USER = "postgres";
    private static final String DB_PASS = "psql";

    /*
     * 商品一覧を取得するSQLです。
     *
     * product_idの小さい順に表示します。
     */
    private static final String SELECT_ALL =
            "SELECT " +
            "product_id, " +
            "product_name, " +
            "price, " +
            "stock, " +
            "category_id, " +
            "description, " +
            "image_url, " +
            "created_at, " +
            "updated_at " +
            "FROM products " +
            "ORDER BY product_id";

    /*
     * 商品IDを指定して商品を1件取得するSQLです。
     */
    private static final String SELECT_BY_ID =
            "SELECT " +
            "product_id, " +
            "product_name, " +
            "price, " +
            "stock, " +
            "category_id, " +
            "description, " +
            "image_url, " +
            "created_at, " +
            "updated_at " +
            "FROM products " +
            "WHERE product_id = ?";

    /*
     * 在庫数を指定された値へ変更するSQLです。
     *
     * 在庫を更新した時刻もupdated_atへ記録します。
     */
    private static final String UPDATE_STOCK =
            "UPDATE products " +
            "SET stock = ?, " +
            "updated_at = CURRENT_TIMESTAMP " +
            "WHERE product_id = ?";

    /*
     * 在庫が足りる場合だけ、購入数量分を減らすSQLです。
     *
     * stock >= ? があるため、
     * 在庫数より多い数量は減らせません。
     */
    private static final String DECREASE_STOCK =
            "UPDATE products " +
            "SET stock = stock - ?, " +
            "updated_at = CURRENT_TIMESTAMP " +
            "WHERE product_id = ? " +
            "AND stock >= ?";

    /**
     * 商品をすべて取得します。
     *
     * @return 商品一覧
     */
    public List<Product> findAll() {

        List<Product> productList = new ArrayList<>();

        try (
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(SELECT_ALL);
            ResultSet rs = ps.executeQuery()
        ) {

            /*
             * 取得した商品を1件ずつProductへ変換し、
             * 商品一覧へ追加します。
             */
            while (rs.next()) {
                productList.add(mapRow(rs));
            }

            return productList;

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "商品一覧の取得に失敗しました。", e);
        }
    }

    /**
     * 商品IDを使って商品を1件検索します。
     *
     * @param productId 商品ID
     * @return 商品が見つかった場合はProduct、
     *         見つからなかった場合はOptional.empty()
     */
    public Optional<Product> findById(long productId) {

        try (
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID)
        ) {

            /*
             * SQLの1番目の「?」へ商品IDを設定します。
             */
            ps.setLong(1, productId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "商品情報の取得に失敗しました。", e);
        }
    }

    /**
     * 商品の在庫数を指定された値へ変更します。
     *
     * @param productId 商品ID
     * @param stock     変更後の在庫数
     * @return 1件更新できた場合はtrue
     */
    public boolean updateStock(long productId, int stock) {

        try (
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(UPDATE_STOCK)
        ) {

            /*
             * 1番目の「?」へ変更後の在庫数を設定します。
             * 2番目の「?」へ商品IDを設定します。
             */
            ps.setInt(1, stock);
            ps.setLong(2, productId);

            /*
             * 更新件数が1件ならtrueです。
             *
             * 商品IDが存在しなければ更新件数が0件になり、
             * falseを返します。
             */
            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "在庫数の更新中にデータベースエラーが発生しました。", e);
        }
    }

    /**
     * 在庫が足りている場合だけ、
     * 購入数量分の在庫を減らします。
     *
     * @param productId 商品ID
     * @param quantity  購入数量
     * @return 在庫を減らせた場合はtrue
     */
    public boolean decreaseStock(long productId, int quantity) {

        try (
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(DECREASE_STOCK)
        ) {

            /*
             * 1番目：在庫から減らす数量
             * 2番目：商品ID
             * 3番目：在庫が足りているか確認する数量
             */
            ps.setInt(1, quantity);
            ps.setLong(2, productId);
            ps.setInt(3, quantity);

            /*
             * 次の場合は更新されずfalseになります。
             *
             * ・商品が存在しない
             * ・在庫が不足している
             */
            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "在庫数の減算中にデータベースエラーが発生しました。", e);
        }
    }

    /**
     * PostgreSQLへ接続します。
     *
     * @return DB接続
     * @throws SQLException DB接続に失敗した場合
     */
    private Connection getConnection() throws SQLException {

        return DriverManager.getConnection(
                JDBC_URL,
                DB_USER,
                DB_PASS);
    }

    /**
     * ResultSetの現在の1行をProductへ変換します。
     *
     * DBのスネークケースと、
     * Javaのキャメルケースを対応させています。
     *
     * @param rs 商品情報を持つResultSet
     * @return Productオブジェクト
     * @throws SQLException データ取得に失敗した場合
     */
    private Product mapRow(ResultSet rs) throws SQLException {

        Product product = new Product();

        product.setProductId(
                rs.getLong("product_id"));

        product.setProductName(
                rs.getString("product_name"));

        product.setPrice(
                rs.getBigDecimal("price"));

        product.setStock(
                rs.getInt("stock"));

        product.setCategoryId(
                rs.getInt("category_id"));

        product.setDescription(
                rs.getString("description"));

        product.setImageUrl(
                rs.getString("image_url"));

        product.setCreatedAt(
                rs.getTimestamp("created_at").toLocalDateTime());

        product.setUpdatedAt(
                rs.getTimestamp("updated_at").toLocalDateTime());

        return product;
    }
}