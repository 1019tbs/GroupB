package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Member;
import com.example.demo.service.PasswordChangeService;

@Controller
public class PasswordChangeController_oonaka {

    private final PasswordChangeService passwordChangeService;

    public PasswordChangeController_oonaka(
            PasswordChangeService passwordChangeService) {

        this.passwordChangeService =
                passwordChangeService;
    }

    /*
     * パスワード変更画面を表示
     */
    @GetMapping("/member/password_oonaka")
    public String showPasswordChange(
            HttpSession session) {

        Member loginMember =
                (Member) session.getAttribute("loginMember");

        if (loginMember == null) {
            return "redirect:/";
        }

        return "PasswordChange_oonaka";
    }

    /*
     * パスワード変更処理
     */
    @PostMapping("/member/password_oonaka")
    public String changePassword(
            @RequestParam(name = "currentPassword")
            String currentPassword,

            @RequestParam(name = "newPassword")
            String newPassword,

            @RequestParam(name = "confirmPassword")
            String confirmPassword,

            HttpSession session,
            Model model) {

        Member loginMember =
                (Member) session.getAttribute("loginMember");

        if (loginMember == null) {
            return "redirect:/";
        }

        /*
         * 新しいパスワードと確認用が一致するか確認
         */
        if (!newPassword.equals(confirmPassword)) {

            model.addAttribute(
                    "errorMsg",
                    "新しいパスワードが一致しません");

            return "PasswordChange_oonaka";
        }

        boolean result =
                passwordChangeService.changePassword(
                        loginMember.getMemberId(),
                        currentPassword,
                        newPassword);

        if (!result) {

            model.addAttribute(
                    "errorMsg",
                    "現在のパスワードまたは入力内容を確認してください");

            return "PasswordChange_oonaka";
        }

        return "passwordChangeComplete_oonaka";
    }
}