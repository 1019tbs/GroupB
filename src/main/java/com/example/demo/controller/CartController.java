package com.example.demo.controller;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.CartItem_oonaka;
import com.example.demo.model.Member;
import com.example.demo.service.CartService;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/cart/add")
    public String addToCart(
            @RequestParam("productId") Long productId,
            @RequestParam("quantity") int quantity,
            HttpSession session) {

        Member loginMember =
                (Member) session.getAttribute("loginMember");

        if (loginMember == null) {
            return "redirect:/index";
        }

        String memberId = loginMember.getMemberId();

        cartService.addToCart(
                memberId,
                productId,
                quantity
        );

        return "redirect:/products_oonaka";
    }
    
    @GetMapping("/cart")
    public String showCart(
            HttpSession session,
            Model model) {

        Member loginMember =
                (Member) session.getAttribute("loginMember");

        if (loginMember == null) {
            return "redirect:/index";
        }

        String memberId =
                loginMember.getMemberId();

        List<CartItem_oonaka> cartList =
                cartService.findCartItems(memberId);

        model.addAttribute(
                "cartList",
                cartList
        );

        return "cart";
    }
}