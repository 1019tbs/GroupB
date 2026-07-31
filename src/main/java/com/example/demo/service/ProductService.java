package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.ProductCartDAO;
import com.example.demo.model.Product_cart;

@Service
public class ProductService {

    @Autowired
    private ProductCartDAO productDAO;

    /*
     * 商品一覧を取得する
     */
    public List<Product_cart> findAll() {

        return productDAO.findAll();

    }

}