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
 * ショッピングカート画面とカート操作を担当します。
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
     * カート内商品の数量を変更します。
     */
    @PostMapping("/cart/update")
    public String updateQuantity(
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
            cartService.updateQuantity(
                    loginMember.getMemberId(),
                    productId,
                    quantity);

            redirectAttributes.addFlashAttribute(
                    "cartMessage",
                    "数量を変更しました。");

        } catch (IllegalArgumentException |
                 IllegalStateException e) {

            redirectAttributes.addFlashAttribute(
                    "cartErrorMessage",
                    e.getMessage());
        }

        return "redirect:/cart";
    }

    /**
     * カートから商品を取り消します。
     */
    @PostMapping("/cart/remove")
    public String removeItem(
            @RequestParam("productId") Long productId,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        Member loginMember =
                getLoginMember(session);

        if (loginMember == null) {
            return "redirect:/";
        }

        try {
            cartService.removeItem(
                    loginMember.getMemberId(),
                    productId);

            redirectAttributes.addFlashAttribute(
                    "cartMessage",
                    "商品をカートから取り消しました。");

        } catch (IllegalArgumentException |
                 IllegalStateException e) {

            redirectAttributes.addFlashAttribute(
                    "cartErrorMessage",
                    e.getMessage());
        }

        return "redirect:/cart";
    }

    /**
     * 現在のloginUserを優先し、
     * 旧コードのloginMemberにも対応します。
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