package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.ProductDAO;
import com.example.demo.model.Product;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final ProductDAO productDAO;

    public List<Product> findAll() {
        return productDAO.findAll();
    }

    public Product findById(long productId) {
        return productDAO.findById(productId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "商品が見つかりません。商品ID: "
                                        + productId
                        )
                );
    }

    @Transactional
    public void updateStock(long productId, int stock) {

        if (stock < 0) {
            throw new IllegalArgumentException(
                    "在庫数は0以上で入力してください。"
            );
        }

        findById(productId);

        if (!productDAO.updateStock(productId, stock)) {
            throw new IllegalStateException(
                    "在庫数を更新できませんでした。"
            );
        }
    }

    @Transactional
    public void decreaseStock(long productId, int quantity) {

        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "購入数量は1以上で入力してください。"
            );
        }

        if (!productDAO.decreaseStock(productId, quantity)) {
            throw new IllegalStateException(
                    "在庫が不足しているか、商品が存在しません。"
            );
        }
    }
}