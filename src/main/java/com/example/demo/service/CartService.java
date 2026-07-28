package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dao.CartDAO;
import com.example.demo.model.CartItem_oonaka;

@Service
public class CartService {

    private CartDAO cartDAO = new CartDAO();

    public void addToCart(String memberId, Long productId, int quantity) {

        Long cartId = cartDAO.findCartIdByMemberId(memberId);

        if (cartId == null) {
            cartId = cartDAO.createCart(memberId);
        }

        cartDAO.addProductToCart(cartId, productId, quantity);
    }
    
    public List<CartItem_oonaka> findCartItems(String memberId) {

        return cartDAO.findCartItems(memberId);
    }
}