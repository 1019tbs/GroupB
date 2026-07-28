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
 */
@Repository
public class ProductDAO {

    private static final String JDBC_URL =
            "jdbc:postgresql://localhost:5432/groupb_project";

    private static final String DB_USER = "postgres";
    private static final String DB_PASS = "psql";

    private static final String PRODUCT_COLUMNS =
            "product_id, " +
            "product_name, " +
            "price, " +
            "stock, " +
            "category_id, " +
            "description, " +
            "image_url, " +
            "active, " +
            "created_at, " +
            "updated_at ";

    /** お客様向け：取扱中の商品だけ取得します。 */
    private static final String SELECT_ALL_ACTIVE =
            "SELECT " + PRODUCT_COLUMNS +
            "FROM products " +
            "WHERE active = TRUE " +
            "ORDER BY product_id";

    /** 管理者向け：取扱停止中の商品も含めて取得します。 */
    private static final String SELECT_ALL_FOR_ADMIN =
            "SELECT " + PRODUCT_COLUMNS +
            "FROM products " +
            "ORDER BY active DESC, product_id";

    private static final String SELECT_BY_ID =
            "SELECT " + PRODUCT_COLUMNS +
            "FROM products " +
            "WHERE product_id = ?";

    private static final String SELECT_ACTIVE_BY_ID =
            "SELECT " + PRODUCT_COLUMNS +
            "FROM products " +
            "WHERE product_id = ? " +
            "AND active = TRUE";

    private static final String EXISTS_BY_NAME =
            "SELECT 1 " +
            "FROM products " +
            "WHERE LOWER(product_name) = LOWER(?) " +
            "LIMIT 1";

    private static final String EXISTS_BY_NAME_EXCEPT_ID =
            "SELECT 1 " +
            "FROM products " +
            "WHERE LOWER(product_name) = LOWER(?) " +
            "AND product_id <> ? " +
            "LIMIT 1";

    private static final String INSERT_PRODUCT =
            "INSERT INTO products (" +
            "product_name, price, stock, category_id, " +
            "description, image_url, active" +
            ") VALUES (?, ?, ?, ?, ?, ?, TRUE)";

    private static final String UPDATE_PRODUCT =
            "UPDATE products SET " +
            "product_name = ?, " +
            "price = ?, " +
            "stock = ?, " +
            "category_id = ?, " +
            "description = ?, " +
            "image_url = ?, " +
            "updated_at = CURRENT_TIMESTAMP " +
            "WHERE product_id = ?";

    private static final String UPDATE_STOCK =
            "UPDATE products SET " +
            "stock = ?, " +
            "updated_at = CURRENT_TIMESTAMP " +
            "WHERE product_id = ?";

    private static final String UPDATE_ACTIVE =
            "UPDATE products SET " +
            "active = ?, " +
            "updated_at = CURRENT_TIMESTAMP " +
            "WHERE product_id = ?";

    /**
     * 在庫が足り、かつ取扱中の場合だけ在庫を減らします。
     */
    private static final String DECREASE_STOCK =
            "UPDATE products SET " +
            "stock = stock - ?, " +
            "updated_at = CURRENT_TIMESTAMP " +
            "WHERE product_id = ? " +
            "AND stock >= ? " +
            "AND active = TRUE";

    /**
     * 既存コードとの互換性を保つ商品一覧取得です。
     * お客様向けを想定し、取扱中だけ返します。
     */
    public List<Product> findAll() {
        return findAllActive();
    }

    /** お客様向けの商品一覧を取得します。 */
    public List<Product> findAllActive() {
        return executeProductListQuery(SELECT_ALL_ACTIVE);
    }

    /** 管理者向けの商品一覧を取得します。 */
    public List<Product> findAllForAdmin() {
        return executeProductListQuery(SELECT_ALL_FOR_ADMIN);
    }

    private List<Product> executeProductListQuery(String sql) {

        List<Product> productList = new ArrayList<>();

        try (
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                productList.add(mapRow(rs));
            }
            return productList;

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "商品一覧の取得に失敗しました。", e);
        }
    }

    /** 取扱状態を問わず商品IDで取得します。 */
    public Optional<Product> findById(long productId) {
        return findOne(SELECT_BY_ID, productId);
    }

    /** 注文処理などで、取扱中の商品だけを商品IDで取得します。 */
    public Optional<Product> findActiveById(long productId) {
        return findOne(SELECT_ACTIVE_BY_ID, productId);
    }

    private Optional<Product> findOne(String sql, long productId) {

        try (
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
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

    /** 同じ商品名が登録済みか確認します。 */
    public boolean existsByProductName(String productName) {

        try (
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(EXISTS_BY_NAME)
        ) {
            ps.setString(1, productName);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "商品名の重複確認に失敗しました。", e);
        }
    }

    /** 編集対象以外に同じ商品名があるか確認します。 */
    public boolean existsByProductNameExceptId(
            String productName,
            long productId) {

        try (
            Connection conn = getConnection();
            PreparedStatement ps =
                    conn.prepareStatement(EXISTS_BY_NAME_EXCEPT_ID)
        ) {
            ps.setString(1, productName);
            ps.setLong(2, productId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "商品名の重複確認に失敗しました。", e);
        }
    }

    /** 新しい商品を登録します。activeは必ずTRUEで登録します。 */
    public boolean insert(Product product) {

        try (
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(INSERT_PRODUCT)
        ) {
            setProductFields(ps, product);
            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "商品の登録中にデータベースエラーが発生しました。", e);
        }
    }

    /** 商品の基本情報と在庫数を更新します。 */
    public boolean update(Product product) {

        try (
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(UPDATE_PRODUCT)
        ) {
            setProductFields(ps, product);
            ps.setLong(7, product.getProductId());
            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "商品情報の更新中にデータベースエラーが発生しました。", e);
        }
    }

    private void setProductFields(
            PreparedStatement ps,
            Product product) throws SQLException {

        ps.setString(1, product.getProductName());
        ps.setBigDecimal(2, product.getPrice());
        ps.setInt(3, product.getStock());
        ps.setInt(4, product.getCategoryId());
        ps.setString(5, product.getDescription());
        ps.setString(6, product.getImageUrl());
    }

    /** 商品の在庫数を指定された値へ変更します。 */
    public boolean updateStock(long productId, int stock) {

        if (stock < 0) {
            throw new IllegalArgumentException(
                    "在庫数は0以上で指定してください。");
        }

        try (
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(UPDATE_STOCK)
        ) {
            ps.setInt(1, stock);
            ps.setLong(2, productId);
            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "在庫数の更新中にデータベースエラーが発生しました。", e);
        }
    }

    /** 商品の取扱状態を変更します。 */
    public boolean updateActive(long productId, boolean active) {

        try (
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(UPDATE_ACTIVE)
        ) {
            ps.setBoolean(1, active);
            ps.setLong(2, productId);
            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "商品の取扱状態の更新中にデータベースエラーが発生しました。", e);
        }
    }

    /** 単独処理として購入数量分だけ在庫を減らします。 */
    public boolean decreaseStock(long productId, int quantity) {

        validateQuantity(quantity);

        try (Connection conn = getConnection()) {
            return decreaseStock(conn, productId, quantity);

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "在庫数の減算中にデータベースエラーが発生しました。", e);
        }
    }

    /**
     * 指定されたConnectionを使って購入数量分だけ在庫を減らします。
     * commit、rollback、closeはService側で行います。
     */
    public boolean decreaseStock(
            Connection conn,
            long productId,
            int quantity) throws SQLException {

        if (conn == null) {
            throw new IllegalArgumentException(
                    "DB接続が指定されていません。");
        }

        validateQuantity(quantity);

        try (PreparedStatement ps =
                conn.prepareStatement(DECREASE_STOCK)) {

            ps.setInt(1, quantity);
            ps.setLong(2, productId);
            ps.setInt(3, quantity);

            return ps.executeUpdate() == 1;
        }
    }

    /** トランザクションで共有するConnectionを取得します。 */
    public Connection openConnection() throws SQLException {
        return DriverManager.getConnection(
                JDBC_URL,
                DB_USER,
                DB_PASS);
    }

    private Connection getConnection() throws SQLException {
        return openConnection();
    }

    private void validateQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "購入数量は1以上で指定してください。");
        }
    }

    private Product mapRow(ResultSet rs) throws SQLException {

        Product product = new Product();

        product.setProductId(rs.getLong("product_id"));
        product.setProductName(rs.getString("product_name"));
        product.setPrice(rs.getBigDecimal("price"));
        product.setStock(rs.getInt("stock"));
        product.setCategoryId(rs.getInt("category_id"));
        product.setDescription(rs.getString("description"));
        product.setImageUrl(rs.getString("image_url"));
        product.setActive(rs.getBoolean("active"));
        product.setCreatedAt(
                rs.getTimestamp("created_at").toLocalDateTime());
        product.setUpdatedAt(
                rs.getTimestamp("updated_at").toLocalDateTime());

        return product;
    }
}