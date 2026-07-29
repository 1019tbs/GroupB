package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dao.CartDAO;
import com.example.demo.model.CartItem_oonaka;

import lombok.RequiredArgsConstructor;

/**
 * ショッピングカート処理を担当します。
 */
@Service
@RequiredArgsConstructor
public class CartService {

    /*
     * new CartDAO()で直接生成せず、
     * SpringからCartDAOを受け取ります。
     */
    private final CartDAO cartDAO;

    public void addToCart(
            String memberId,
            Long productId,
            int quantity) {

        if (memberId == null || memberId.isBlank()) {
            throw new IllegalArgumentException(
                    "会員IDが指定されていません。");
        }

        if (productId == null) {
            throw new IllegalArgumentException(
                    "商品IDが指定されていません。");
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "数量は1以上で指定してください。");
        }

        Long cartId =
                cartDAO.findCartIdByMemberId(memberId);

        if (cartId == null) {
            cartId = cartDAO.createCart(memberId);
        }

        if (cartId == null) {
            throw new IllegalStateException(
                    "ショッピングカートを作成できませんでした。");
        }

        cartDAO.addProductToCart(
                cartId,
                productId,
                quantity);
    }

    public List<CartItem_oonaka> findCartItems(
            String memberId) {

        return cartDAO.findCartItems(memberId);
    }
}