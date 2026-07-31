package com.example.demo.service;

import java.util.List;

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

    private final CartDAO cartDAO;
    private final ProductDAO productDAO;

    /**
     * 商品をカートへ追加します。
     */
    public void addToCart(
            String memberId,
            Long productId,
            int quantity) {

        validateMemberId(memberId);
        validateProductId(productId);
        validateQuantity(quantity);

        Product product =
                findPurchasableProduct(productId);

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
            cartId = cartDAO.createCart(memberId);
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
    }

    public List<CartItem> findCartItems(
            String memberId) {

        validateMemberId(memberId);

        return cartDAO.findCartItems(memberId);
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
}