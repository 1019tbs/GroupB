package com.example.demo.service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.example.demo.dao.ProductDAO;
import com.example.demo.model.Product;

import lombok.RequiredArgsConstructor;

/**
 * 管理者向けの商品・在庫管理処理を担当します。
 */
@Service
@RequiredArgsConstructor
public class InventoryService {

    private static final BigDecimal MAX_PRICE =
            new BigDecimal("999999999999");

    private static final Set<Integer> VALID_CATEGORY_IDS =
            Set.of(1, 2, 3);

    /**
     * 管理画面で選択できる画像です。
     * キー：DBへ保存するパス
     * 値：画面に表示する名前
     */
    private static final Map<String, String> IMAGE_OPTIONS =
            createImageOptions();

    private final ProductDAO productDAO;

    /** 管理画面用：取扱停止中を含む全商品を取得します。 */
    public List<Product> findAll() {
        return productDAO.findAllForAdmin();
    }

    /** お客様画面用：取扱中の商品だけを取得します。 */
    public List<Product> findAllActive() {
        return productDAO.findAllActive();
    }

    public Product findById(long productId) {
        return productDAO.findById(productId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "商品が見つかりません。商品ID: "
                                        + productId));
    }

    public Map<String, String> getImageOptions() {
        return IMAGE_OPTIONS;
    }

    /** 新しい商品を登録します。 */
    public void register(Product product) {

        normalizeAndValidate(product);

        if (productDAO.existsByProductName(
                product.getProductName())) {
            throw new IllegalArgumentException(
                    "同じ商品名が既に登録されています。" +
                    "取扱停止中の商品は「取扱再開」を使用してください。");
        }

        product.setActive(true);

        if (!productDAO.insert(product)) {
            throw new IllegalStateException(
                    "商品を登録できませんでした。");
        }
    }

    /** 商品名・価格・在庫・カテゴリー・説明・画像を更新します。 */
    public void updateProduct(Product product) {

        if (product.getProductId() == null) {
            throw new IllegalArgumentException(
                    "商品IDが指定されていません。");
        }

        findById(product.getProductId());
        normalizeAndValidate(product);

        if (productDAO.existsByProductNameExceptId(
                product.getProductName(),
                product.getProductId())) {
            throw new IllegalArgumentException(
                    "同じ商品名が既に登録されています。");
        }

        if (!productDAO.update(product)) {
            throw new IllegalStateException(
                    "商品情報を更新できませんでした。");
        }
    }

    /** 在庫数だけを一覧画面から更新します。 */
    public void updateStock(long productId, int stock) {

        if (stock < 0) {
            throw new IllegalArgumentException(
                    "在庫数は0以上で入力してください。");
        }

        if (!productDAO.updateStock(productId, stock)) {
            throw new IllegalStateException(
                    "商品が見つかりません。商品ID: "
                            + productId);
        }
    }

    /** 商品を論理削除して取扱停止にします。在庫数は残します。 */
    public void stopProduct(long productId) {

        Product product = findById(productId);

        if (Boolean.FALSE.equals(product.getActive())) {
            throw new IllegalArgumentException(
                    "この商品は既に取扱停止中です。");
        }

        if (!productDAO.updateActive(productId, false)) {
            throw new IllegalStateException(
                    "商品の取扱いを停止できませんでした。");
        }
    }

    /** 論理削除された商品を取扱再開します。 */
    public void resumeProduct(long productId) {

        Product product = findById(productId);

        if (Boolean.TRUE.equals(product.getActive())) {
            throw new IllegalArgumentException(
                    "この商品は既に取扱中です。");
        }

        if (!productDAO.updateActive(productId, true)) {
            throw new IllegalStateException(
                    "商品の取扱いを再開できませんでした。");
        }
    }

    /** 単品の在庫減算です。複数注文ではConnection共有版を使います。 */
    public void decreaseStock(long productId, int quantity) {

        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "購入数量は1以上で入力してください。");
        }

        if (!productDAO.decreaseStock(productId, quantity)) {
            throw new IllegalStateException(
                    "在庫不足、取扱停止、または商品が存在しません。");
        }
    }

    private void normalizeAndValidate(Product product) {

        if (product == null) {
            throw new IllegalArgumentException(
                    "商品情報が指定されていません。");
        }

        String productName = product.getProductName();

        if (productName == null || productName.isBlank()) {
            throw new IllegalArgumentException(
                    "商品名を入力してください。");
        }

        productName = productName.trim();
        product.setProductName(productName);

        if (productName.length() > 100) {
            throw new IllegalArgumentException(
                    "商品名は100文字以内で入力してください。");
        }

        BigDecimal price = product.getPrice();

        if (price == null || price.compareTo(BigDecimal.ONE) < 0) {
            throw new IllegalArgumentException(
                    "価格は1円以上で入力してください。");
        }

        if (price.stripTrailingZeros().scale() > 0) {
            throw new IllegalArgumentException(
                    "価格は1円単位の整数で入力してください。");
        }

        if (price.compareTo(MAX_PRICE) > 0) {
            throw new IllegalArgumentException(
                    "価格は999,999,999,999円以下で入力してください。");
        }

        if (product.getStock() == null || product.getStock() < 0) {
            throw new IllegalArgumentException(
                    "在庫数は0以上で入力してください。");
        }

        if (product.getCategoryId() == null
                || !VALID_CATEGORY_IDS.contains(
                        product.getCategoryId())) {
            throw new IllegalArgumentException(
                    "カテゴリーを選択してください。");
        }

        boolean pickupAvailable =
                Boolean.TRUE.equals(
                        product.getPickupAvailable());

        boolean deliveryAvailable =
                Boolean.TRUE.equals(
                        product.getDeliveryAvailable());

        if (!pickupAvailable && !deliveryAvailable) {
            throw new IllegalArgumentException(
                    "店頭受取または通販のどちらかを選択してください。");
        }

        product.setPickupAvailable(pickupAvailable);
        product.setDeliveryAvailable(deliveryAvailable);

        String description = product.getDescription();

        if (description != null) {
            description = description.trim();

            if (description.isEmpty()) {
                description = null;
            } else if (description.length() > 1000) {
                throw new IllegalArgumentException(
                        "商品説明は1000文字以内で入力してください。");
            }
        }

        product.setDescription(description);

        String imageUrl = product.getImageUrl();

        if (imageUrl == null || !IMAGE_OPTIONS.containsKey(imageUrl)) {
            throw new IllegalArgumentException(
                    "商品画像を選択してください。");
        }
    }

    private static Map<String, String> createImageOptions() {

        Map<String, String> options = new LinkedHashMap<>();

        options.put("/images/cake_opera.png", "ラズベリー・オペラ");
        options.put("/images/cake_honey.png", "ハニカム・ムースケーキ");
        options.put("/images/cake_bkforest.png", "ブラックフォレスト・ドーム");
        options.put("/images/cake_batten.png", "バッテンバーグケーキ");
        options.put("/images/cake_shuu.png", "クロカンブッシュケーキ");
        options.put("/images/cake_moose.png", "アールグレイムースケーキ");

        options.put("/images/bakes_classic.png", "クラシック焼き菓子セット");
        options.put("/images/bakes_fruity.png", "フルーティータイムセット");
        options.put("/images/bakes_nuts.png", "Honey & ナッツタイムセット");

        options.put("/images/pastries_quiche.png", "ほうれん草とベーコンのキッシュ");
        options.put("/images/pastries_knkpie.png", "きのことチェダーの三角ハンドパイ");
        options.put("/images/pastries_galette.png", "トマトとリコッタのガレット");
        options.put("/images/pastries_apple.png", "ハニーアップルパイ");
        options.put("/images/pastries_millef.png", "ベリーピスタチオミルフィーユ");
        options.put("/images/pastries_choco.png", "ヘーゼルナッツチョコパイ");

        options.put("/images/cake.png", "汎用ケーキ画像");
        options.put("/images/cookies.png", "汎用クッキー画像");
        options.put("/images/otherSweets.png", "汎用スイーツ画像");

        return Collections.unmodifiableMap(options);
    }
}
