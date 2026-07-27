package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.ProductDAO_oonaka;
import com.example.demo.model.Product_oonaka;

@Service
public class ProductService_oonaka {

    @Autowired
    private ProductDAO_oonaka productDAO;

    /*
     * 商品一覧を取得する
     */
    public List<Product_oonaka> findAll() {

        return productDAO.findAll();

    }

}