package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dao.ProductDAO;
import com.example.demo.model.Product;

import lombok.RequiredArgsConstructor;

/**
 * 在庫管理に関する処理を担当するServiceクラスです。
 *
 * ControllerとProductDAOの間に入り、
 * 入力値の確認や処理結果の判定を行います。
 */
@Service
@RequiredArgsConstructor
public class InventoryService {

    private final ProductDAO productDAO;

    /**
     * 商品をすべて取得します。
     *
     * @return 商品一覧
     */
    public List<Product> findAll() {

        return productDAO.findAll();
    }

    /**
     * 商品IDを使って商品を1件取得します。
     *
     * @param productId 商品ID
     * @return 商品情報
     */
    public Product findById(long productId) {

        validateProductId(productId);

        return productDAO.findById(productId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "商品が見つかりません。商品ID: "
                                        + productId));
    }

    /**
     * 商品の在庫数を指定された値へ変更します。
     *
     * @param productId 商品ID
     * @param stock     変更後の在庫数
     */
    public void updateStock(long productId, int stock) {

        validateProductId(productId);

        /*
         * マイナス在庫になることを防ぎます。
         */
        if (stock < 0) {
            throw new IllegalArgumentException(
                    "在庫数は0以上で入力してください。");
        }

        /*
         * 更新件数が0件の場合は、
         * 指定された商品が存在しません。
         */
        if (!productDAO.updateStock(productId, stock)) {
            throw new IllegalStateException(
                    "商品が見つかりません。商品ID: "
                            + productId);
        }
    }

    /**
     * 購入数量分だけ在庫を減らします。
     *
     * @param productId 商品ID
     * @param quantity  購入数量
     */
    public void decreaseStock(long productId, int quantity) {

        validateProductId(productId);

        /*
         * 0個やマイナス個数での処理を防ぎます。
         */
        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "購入数量は1以上で入力してください。");
        }

        /*
         * 商品が存在しない場合や、
         * 在庫が足りない場合は更新されません。
         */
        if (!productDAO.decreaseStock(productId, quantity)) {
            throw new IllegalStateException(
                    "在庫が不足しているか、商品が存在しません。");
        }
    }

    /**
     * 商品IDが正しい値か確認します。
     *
     * @param productId 商品ID
     */
    private void validateProductId(long productId) {

        if (productId <= 0) {
            throw new IllegalArgumentException(
                    "商品IDは1以上で指定してください。");
        }
    }
}