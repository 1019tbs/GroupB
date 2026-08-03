package com.example.demo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.model.CartItem;
import com.example.demo.model.Product_cart;

/**
 * carts・cart_itemsテーブルへのアクセスを担当します。
 */
@Repository
public class CartDAO {

    private static final String JDBC_URL =
            "jdbc:postgresql://localhost:5432/groupb_project";

    private static final String DB_USER =
            "postgres";

    private static final String DB_PASS =
            "psql";

    /**
     * 会員のカートIDを取得します。
     */
    public Long findCartIdByMemberId(
            String memberId) {

        try (Connection conn =
                getConnection()) {

            return findCartIdByMemberId(
                    conn,
                    memberId,
                    false);

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "カート情報の取得に失敗しました。",
                    e);
        }
    }

    /**
     * 注文トランザクション用です。
     * カート行をロックしてカートIDを取得します。
     */
    public Long findCartIdByMemberId(
            Connection conn,
            String memberId)
            throws SQLException {

        return findCartIdByMemberId(
                conn,
                memberId,
                true);
    }

    private Long findCartIdByMemberId(
            Connection conn,
            String memberId,
            boolean forUpdate)
            throws SQLException {

        validateConnection(conn);

        String sql =
                "SELECT cart_id "
              + "FROM carts "
              + "WHERE member_id = ?"
              + (forUpdate
                    ? " FOR UPDATE"
                    : "");

        try (PreparedStatement ps =
                conn.prepareStatement(sql)) {

            ps.setString(1, memberId);

            try (ResultSet rs =
                    ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getLong(
                            "cart_id");
                }
            }
        }

        return null;
    }

    /**
     * 会員用カートを新規作成します。
     */
    public Long createCart(
            String memberId) {

        return createCart(memberId, "DELIVERY");
    }

    public Long createCart(
            String memberId,
            String fulfillmentMethod) {

        String sql =
                "INSERT INTO carts "
              + "(member_id, fulfillment_method) "
              + "VALUES (?, ?) "
              + "RETURNING cart_id";

        try (
            Connection conn = getConnection();
            PreparedStatement ps =
                    conn.prepareStatement(sql)
        ) {

            ps.setString(1, memberId);
            ps.setString(2, fulfillmentMethod);

            try (ResultSet rs =
                    ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getLong(
                            "cart_id");
                }
            }

            throw new IllegalStateException(
                    "カートIDを取得できませんでした。");

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "ショッピングカートの作成に失敗しました。",
                    e);
        }
    }

    public String findFulfillmentMethod(
            String memberId) {

        String sql =
                "SELECT fulfillment_method "
              + "FROM carts "
              + "WHERE member_id = ?";

        try (
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, memberId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next()
                        ? rs.getString("fulfillment_method")
                        : null;
            }

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "カートの受取方法を取得できませんでした。", e);
        }
    }

    public String findFulfillmentMethod(
            Connection conn,
            long cartId) throws SQLException {

        validateConnection(conn);

        String sql =
                "SELECT fulfillment_method "
              + "FROM carts "
              + "WHERE cart_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, cartId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next()
                        ? rs.getString("fulfillment_method")
                        : null;
            }
        }
    }

    public boolean updateFulfillmentMethod(
            long cartId,
            String fulfillmentMethod) {

        String sql =
                "UPDATE carts "
              + "SET fulfillment_method = ?, "
              + "updated_at = CURRENT_TIMESTAMP "
              + "WHERE cart_id = ?";

        try (
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, fulfillmentMethod);
            ps.setLong(2, cartId);
            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "カートの受取方法を設定できませんでした。", e);
        }
    }

    public void resetFulfillmentMethodIfEmpty(
            String memberId) {

        String sql =
                "UPDATE carts c "
              + "SET fulfillment_method = NULL, "
              + "updated_at = CURRENT_TIMESTAMP "
              + "WHERE c.member_id = ? "
              + "AND NOT EXISTS ("
              + "SELECT 1 FROM cart_items ci "
              + "WHERE ci.cart_id = c.cart_id)";

        try (
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, memberId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "カート情報を更新できませんでした。", e);
        }
    }

    /**
     * カートに入っている指定商品の数量を取得します。
     */
    public int findQuantity(
            String memberId,
            long productId) {

        String sql =
                "SELECT ci.quantity "
              + "FROM carts c "
              + "JOIN cart_items ci "
              + "ON c.cart_id = ci.cart_id "
              + "WHERE c.member_id = ? "
              + "AND ci.product_id = ?";

        try (
            Connection conn = getConnection();
            PreparedStatement ps =
                    conn.prepareStatement(sql)
        ) {

            ps.setString(1, memberId);
            ps.setLong(2, productId);

            try (ResultSet rs =
                    ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt(
                            "quantity");
                }
            }

            return 0;

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "カート内数量の取得に失敗しました。",
                    e);
        }
    }

    /**
     * 商品をカートへ追加します。
     */
    public void addProductToCart(
            Long cartId,
            Long productId,
            int quantity) {

        String sql =
                "INSERT INTO cart_items "
              + "(cart_id, product_id, quantity) "
              + "VALUES (?, ?, ?) "
              + "ON CONFLICT (cart_id, product_id) "
              + "DO UPDATE SET "
              + "quantity = cart_items.quantity "
              + "+ EXCLUDED.quantity, "
              + "updated_at = CURRENT_TIMESTAMP";

        try (
            Connection conn = getConnection();
            PreparedStatement ps =
                    conn.prepareStatement(sql)
        ) {

            ps.setLong(1, cartId);
            ps.setLong(2, productId);
            ps.setInt(3, quantity);

            if (ps.executeUpdate() != 1) {
                throw new IllegalStateException(
                        "商品をカートへ追加できませんでした。");
            }

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "商品をカートへ追加できませんでした。",
                    e);
        }
    }

    /**
     * カート内数量を変更します。
     */
    public boolean updateQuantity(
            String memberId,
            long productId,
            int quantity) {

        String sql =
                "UPDATE cart_items "
              + "SET quantity = ?, "
              + "updated_at = CURRENT_TIMESTAMP "
              + "WHERE cart_id = ("
              + "    SELECT cart_id "
              + "    FROM carts "
              + "    WHERE member_id = ?"
              + ") "
              + "AND product_id = ?";

        try (
            Connection conn = getConnection();
            PreparedStatement ps =
                    conn.prepareStatement(sql)
        ) {

            ps.setInt(1, quantity);
            ps.setString(2, memberId);
            ps.setLong(3, productId);

            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "カート内数量の変更に失敗しました。",
                    e);
        }
    }

    /**
     * カートから商品を取り消します。
     */
    public boolean removeItem(
            String memberId,
            long productId) {

        String sql =
                "DELETE FROM cart_items "
              + "WHERE cart_id = ("
              + "    SELECT cart_id "
              + "    FROM carts "
              + "    WHERE member_id = ?"
              + ") "
              + "AND product_id = ?";

        try (
            Connection conn = getConnection();
            PreparedStatement ps =
                    conn.prepareStatement(sql)
        ) {

            ps.setString(1, memberId);
            ps.setLong(2, productId);

            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "カート商品の取消に失敗しました。",
                    e);
        }
    }

    /**
     * 通常のカート画面用です。
     */
    public List<CartItem>
            findCartItems(
                    String memberId) {

        try (Connection conn =
                getConnection()) {

            return findCartItems(
                    conn,
                    memberId,
                    false);

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "カート内容の取得に失敗しました。",
                    e);
        }
    }

    /**
     * 注文トランザクション用です。
     *
     * cart_itemsとproductsをロックし、
     * 読み取った後に数量・価格・取扱状態が
     * 同時更新されにくい状態にします。
     */
    public List<CartItem>
            findCartItems(
                    Connection conn,
                    String memberId)
                    throws SQLException {

        return findCartItems(
                conn,
                memberId,
                true);
    }

    private List<CartItem>
            findCartItems(
                    Connection conn,
                    String memberId,
                    boolean forUpdate)
                    throws SQLException {

        validateConnection(conn);

        List<CartItem> cartList =
                new ArrayList<>();

        String sql =
                "SELECT "
              + "p.product_id, "
              + "p.product_name, "
              + "p.price, "
              + "p.stock, "
              + "p.category_id, "
              + "p.description, "
              + "p.image_url, "
              + "p.active, "
              + "p.pickup_available, "
              + "p.delivery_available, "
              + "p.created_at, "
              + "p.updated_at, "
              + "ci.quantity "
              + "FROM carts c "
              + "JOIN cart_items ci "
              + "ON c.cart_id = ci.cart_id "
              + "JOIN products p "
              + "ON ci.product_id = p.product_id "
              + "WHERE c.member_id = ? "
              + (forUpdate
                    ? "ORDER BY p.product_id "
                      + "FOR UPDATE OF ci, p"
                    : "ORDER BY ci.cart_item_id");

        try (PreparedStatement ps =
                conn.prepareStatement(sql)) {

            ps.setString(1, memberId);

            try (ResultSet rs =
                    ps.executeQuery()) {

                while (rs.next()) {
                    cartList.add(
                            mapCartItem(rs));
                }
            }
        }

        return cartList;
    }

    /**
     * 注文完了後にカート商品をすべて削除します。
     */
    public boolean clearCart(
            Connection conn,
            long cartId)
            throws SQLException {

        validateConnection(conn);

        String sql =
                "DELETE FROM cart_items "
              + "WHERE cart_id = ?";

        try (PreparedStatement ps =
                conn.prepareStatement(sql)) {

            ps.setLong(1, cartId);

            boolean deleted = ps.executeUpdate() > 0;

            if (!deleted) {
                return false;
            }

            try (PreparedStatement resetPs =
                    conn.prepareStatement(
                            "UPDATE carts "
                          + "SET fulfillment_method = NULL, "
                          + "updated_at = CURRENT_TIMESTAMP "
                          + "WHERE cart_id = ?")) {

                resetPs.setLong(1, cartId);
                return resetPs.executeUpdate() == 1;
            }
        }
    }

    private CartItem mapCartItem(
            ResultSet rs)
            throws SQLException {

        Product_cart product =
                new Product_cart();

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

        product.setActive(
                rs.getBoolean("active"));

        product.setPickupAvailable(
                rs.getBoolean("pickup_available"));

        product.setDeliveryAvailable(
                rs.getBoolean("delivery_available"));

        product.setCreatedAt(
                rs.getTimestamp("created_at")
                        .toLocalDateTime());

        product.setUpdatedAt(
                rs.getTimestamp("updated_at")
                        .toLocalDateTime());

        return new CartItem(
                product,
                rs.getInt("quantity"));
    }

    private void validateConnection(
            Connection conn) {

        if (conn == null) {
            throw new IllegalArgumentException(
                    "DB接続が指定されていません。");
        }
    }

    private Connection getConnection()
            throws SQLException {

        return DriverManager.getConnection(
                JDBC_URL,
                DB_USER,
                DB_PASS);
    }
}
