package com.example.demo.controller;

import java.time.LocalDate;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.CheckoutForm;
import com.example.demo.model.Member;
import com.example.demo.service.CartService;
import com.example.demo.service.CheckoutService;
import com.example.demo.service.InventoryService;

import lombok.RequiredArgsConstructor;

/**
 * メニュー画面から店頭受取予約を開始します。
 *
 * 商品を店頭受取用カートへ追加し、既存のチェックアウト確認画面へ
 * 接続することで、在庫確認と注文登録を共通化します。
 */
@Controller
@RequiredArgsConstructor
public class PickupReservationController {

    private final CartService cartService;
    private final CheckoutService checkoutService;
    private final InventoryService inventoryService;

//    @PostMapping("/pickup/start")
    public String startPickupReservation(
            @RequestParam(
                    value = "productId",
                    required = false)
            Long productId,
            @RequestParam(
                    value = "quantity",
                    defaultValue = "1")
            int quantity,
            @ModelAttribute
            CheckoutForm checkoutForm,
            HttpSession session,
            Model model) {

        Member loginMember =
                getLoginMember(session);

        if (loginMember == null) {
            return "redirect:/";
        }

        checkoutForm.setFulfillmentMethod(
                "PICKUP");
        checkoutForm.setPaymentMethod(
                "pay_at_store");

        try {
            if (productId == null) {
                throw new IllegalArgumentException(
                        "予約する商品を選択してください。");
            }

            checkoutService.validateAndNormalize(
                    checkoutForm);

            cartService.addToCart(
                    loginMember.getMemberId(),
                    productId,
                    quantity,
                    "PICKUP");

            return "forward:/checkout/confirm";

        } catch (IllegalArgumentException |
                 IllegalStateException e) {

            model.addAttribute(
                    "productList",
                    inventoryService.findAllActive());
            model.addAttribute(
                    "minPickupDate",
                    LocalDate.now());
            model.addAttribute(
                    "errorMsg",
                    e.getMessage());

            return "menu";
        }
    }

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
