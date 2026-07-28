package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.model.Member;

@Controller
public class AdminController {

    /**
     * 管理者メイン画面を表示
     */
    @GetMapping("/admin")
    public String showAdmin(
            HttpSession session,
            Model model) {

        Member loginMember =
                (Member) session.getAttribute("loginMember");

        // 未ログインの場合
//        if (loginMember == null) {
//
//            model.addAttribute(
//                    "errorMsg",
//                    "ログインしてください");
//
//            return "redirect:/";
//        }

        // 管理者以外の場合
        if (!"admin".equals(loginMember.getRole())) {

            model.addAttribute(
                    "errorMsg",
                    "管理者のみ閲覧できます");

            return "redirect:/main";
        }

        return "admin";
    }
}