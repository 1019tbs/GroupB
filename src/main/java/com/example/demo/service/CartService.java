package com.example.demo.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.example.demo.dao.CartDAO;
import com.example.demo.dao.ProductDAO;
import com.example.demo.model.CartItem;
import com.example.demo.model.Product;

import lombok.RequiredArgsConstructor;

/**
 * ショッピングカート処理を担当します。
 */
@Service
@RequiredArgsConstructor
public class CartService {

    private static final Set<String> VALID_FULFILLMENT_METHODS =
            Set.of("DELIVERY", "PICKUP");

    private final CartDAO cartDAO;
    private final ProductDAO productDAO;

    /**
     * 商品をカートへ追加します。
     */
    public void addToCart(
            String memberId,
            Long productId,
            int quantity) {

        addToCart(
                memberId,
                productId,
                quantity,
                "DELIVERY");
    }

    public void addToCart(
            String memberId,
            Long productId,
            int quantity,
            String fulfillmentMethod) {

        validateMemberId(memberId);
        validateProductId(productId);
        validateQuantity(quantity);
        validateFulfillmentMethod(fulfillmentMethod);

        Product product =
                findPurchasableProduct(productId);

        validateProductAvailability(
                product,
                fulfillmentMethod);

        int currentQuantity =
                cartDAO.findQuantity(
                        memberId,
                        productId);

        long quantityAfterAddition =
                (long) currentQuantity + quantity;

        if (quantityAfterAddition > product.getStock()) {
            throw new IllegalArgumentException(
                    "在庫数を超えて追加できません。"
                    + "現在のカート数量: "
                    + currentQuantity
                    + "、在庫数: "
                    + product.getStock());
        }

        Long cartId =
                cartDAO.findCartIdByMemberId(memberId);

        if (cartId == null) {
            cartId = cartDAO.createCart(
                    memberId,
                    fulfillmentMethod);
        } else {
            String currentMethod =
                    cartDAO.findFulfillmentMethod(memberId);

            if (currentMethod == null) {
                if (!cartDAO.updateFulfillmentMethod(
                        cartId,
                        fulfillmentMethod)) {

                    throw new IllegalStateException(
                            "カートの受取方法を設定できませんでした。");
                }

            } else if (!currentMethod.equals(
                    fulfillmentMethod)) {

                throw new IllegalArgumentException(
                        "カートには別の受取方法の商品が入っています。"
                        + "現在の注文を完了するか、カートを空にしてください。");
            }
        }

        cartDAO.addProductToCart(
                cartId,
                productId,
                quantity);
    }

    /**
     * カート内商品の数量を指定した数へ変更します。
     */
    public void updateQuantity(
            String memberId,
            Long productId,
            int quantity) {

        validateMemberId(memberId);
        validateProductId(productId);
        validateQuantity(quantity);

        Product product =
                findPurchasableProduct(productId);

        if (quantity > product.getStock()) {
            throw new IllegalArgumentException(
                    "在庫数を超える数量には変更できません。"
                    + "在庫数: "
                    + product.getStock());
        }

        if (!cartDAO.updateQuantity(
                memberId,
                productId,
                quantity)) {

            throw new IllegalStateException(
                    "変更対象の商品がカートにありません。");
        }
    }

    /**
     * カートから商品を取り消します。
     */
    public void removeItem(
            String memberId,
            Long productId) {

        validateMemberId(memberId);
        validateProductId(productId);

        if (!cartDAO.removeItem(
                memberId,
                productId)) {

            throw new IllegalStateException(
                    "取消対象の商品がカートにありません。");
        }

        cartDAO.resetFulfillmentMethodIfEmpty(memberId);
    }

    public List<CartItem> findCartItems(
            String memberId) {

        validateMemberId(memberId);

        return cartDAO.findCartItems(memberId);
    }

    public String getFulfillmentMethod(
            String memberId) {

        validateMemberId(memberId);
        return cartDAO.findFulfillmentMethod(memberId);
    }

    /**
     * 取扱中かつ存在する商品だけを返します。
     */
    private Product findPurchasableProduct(
            long productId) {

        Product product =
                productDAO.findActiveById(productId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "商品が存在しないか、"
                                        + "現在は取扱停止中です。"));

        if (product.getStock() == null
                || product.getStock() <= 0) {

            throw new IllegalArgumentException(
                    "この商品は在庫切れです。");
        }

        return product;
    }

    private void validateMemberId(
            String memberId) {

        if (memberId == null
                || memberId.isBlank()) {

            throw new IllegalArgumentException(
                    "会員IDが指定されていません。");
        }
    }

    private void validateProductId(
            Long productId) {

        if (productId == null) {
            throw new IllegalArgumentException(
                    "商品IDが指定されていません。");
        }
    }

    private void validateQuantity(
            int quantity) {

        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "数量は1以上で指定してください。");
        }
    }

    private void validateFulfillmentMethod(
            String fulfillmentMethod) {

        if (fulfillmentMethod == null
                || !VALID_FULFILLMENT_METHODS.contains(
                        fulfillmentMethod)) {

            throw new IllegalArgumentException(
                    "受取方法が正しくありません。");
        }
    }

    private void validateProductAvailability(
            Product product,
            String fulfillmentMethod) {

        if ("DELIVERY".equals(fulfillmentMethod)
                && !Boolean.TRUE.equals(
                        product.getDeliveryAvailable())) {

            throw new IllegalArgumentException(
                    "この商品は通販に対応していません。");
        }

        if ("PICKUP".equals(fulfillmentMethod)
                && !Boolean.TRUE.equals(
                        product.getPickupAvailable())) {

            throw new IllegalArgumentException(
                    "この商品は店頭受取に対応していません。");
        }
    }
}
