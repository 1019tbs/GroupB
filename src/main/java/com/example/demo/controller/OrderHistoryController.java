package com.example.demo.controller;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.Member;
import com.example.demo.model.ShoppingOrder;
import com.example.demo.service.OrderHistoryService;

import lombok.RequiredArgsConstructor;

/**
 * ログイン会員の注文履歴画面を担当します。
 */
@Controller
@RequiredArgsConstructor
public class OrderHistoryController {

    private final OrderHistoryService
            orderHistoryService;

    @GetMapping("/orders/history")
    public String showHistory(
            HttpSession session,
            Model model) {

        Member loginMember =
                getLoginMember(session);

        if (loginMember == null) {
            return "redirect:/";
        }

        List<ShoppingOrder> orderList =
                orderHistoryService
                        .findHistory(
                                loginMember
                                        .getMemberId());

        model.addAttribute(
                "orderList",
                orderList);

        return "orderHistory";
    }

    @GetMapping("/orders/history/detail")
    public String showDetail(
            @RequestParam("orderId")
            long orderId,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {

        Member loginMember =
                getLoginMember(session);

        if (loginMember == null) {
            return "redirect:/";
        }

        try {
            ShoppingOrder order =
                    orderHistoryService
                            .findDetail(
                                    loginMember
                                            .getMemberId(),
                                    orderId);

            model.addAttribute(
                    "order",
                    order);

            return "orderHistoryDetail";

        } catch (IllegalArgumentException |
                 IllegalStateException e) {

            redirectAttributes.addFlashAttribute(
                    "historyErrorMessage",
                    e.getMessage());

            return "redirect:/orders/history";
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