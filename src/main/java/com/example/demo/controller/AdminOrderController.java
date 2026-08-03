package com.example.demo.controller;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.dao.OrderDAO;
import com.example.demo.model.Member;
import com.example.demo.model.Order;

@Controller
public class AdminOrderController {

    @Autowired
    private OrderDAO orderDAO;

    /**
     * 管理者用の予約一覧画面を表示
     */
    @GetMapping("/admin/order")
    public String showOrderList(
            HttpSession session,
            Model model) {

        Member loginMember =
                (Member) session.getAttribute("loginMember");

        
        // 未ログインの場合
        if (loginMember == null) {
            return "redirect:/";
        }

        // 管理者以外の場合
        if (!"admin".equals(loginMember.getRole())) {
            return "redirect:/main";
        }

        List<Order> orderList =
                orderDAO.findAll();

        model.addAttribute(
                "orderList",
                orderList);

        return "adminOrder";
    }
}