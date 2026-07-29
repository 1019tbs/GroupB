package com.example.demo.controller;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.CartItem_oonaka;
import com.example.demo.model.Member;
import com.example.demo.service.CartService;

import lombok.RequiredArgsConstructor;

/**
 * ショッピングカート画面とカート追加処理を担当します。
 */
@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    /**
     * メニュー画面から商品をカートへ追加します。
     */
    @PostMapping("/cart/add")
    public String addToCart(
            @RequestParam("productId") Long productId,
            @RequestParam("quantity") int quantity,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        Member loginMember =
                getLoginMember(session);

        if (loginMember == null) {
            return "redirect:/";
        }

        try {
            cartService.addToCart(
                    loginMember.getMemberId(),
                    productId,
                    quantity);

            redirectAttributes.addFlashAttribute(
                    "cartMessage",
                    "商品をカートに追加しました。");

        } catch (IllegalArgumentException |
                 IllegalStateException e) {

            redirectAttributes.addFlashAttribute(
                    "cartErrorMessage",
                    e.getMessage());
        }

        /*
         * 商品を続けて選べるよう、追加後はメニューへ戻します。
         */
        return "redirect:/menu";
    }

    /**
     * ログイン会員のカート内容を表示します。
     */
    @GetMapping("/cart")
    public String showCart(
            HttpSession session,
            Model model) {

        Member loginMember =
                getLoginMember(session);

        if (loginMember == null) {
            return "redirect:/";
        }

        List<CartItem_oonaka> cartList =
                cartService.findCartItems(
                        loginMember.getMemberId());

        model.addAttribute(
                "cartList",
                cartList);

        return "cart";
    }

    /**
     * 現在のログイン処理で使用しているloginUserを優先し、
     * クラスメイトの旧コードで使用していたloginMemberにも対応します。
     */
    private Member getLoginMember(
            HttpSession session) {

        Object loginUser =
                session.getAttribute("loginUser");

        if (loginUser instanceof Member member) {
            return member;
        }

        Object loginMember =
                session.getAttribute("loginMember");

        if (loginMember instanceof Member member) {
            return member;
        }

        return null;
    }
}