package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dao.LoginListDAO;
import com.example.demo.model.Member;

@Controller
public class LoginController {

    /*
     * ログイン認証DAO
     */
    @Autowired
    private LoginListDAO loginListDAO;

    /**
     * ログイン画面表示
     */
    @GetMapping({"/", "/index"})
    public String showLogin() {

        return "index";
    }

    /**
     * ログイン処理
     */
    @PostMapping("/Login")
    public String login(

            @RequestParam(name = "name")
            String name,

            @RequestParam(name = "pass")
            String pass,

            // セッションを追加
            HttpSession session,

            Model model) {

        // 会員ID未入力
        if (name == null || name.isBlank()) {

            model.addAttribute(
                    "errorMsg",
                    "会員IDを入力してください");

            return "index";
        }

        // パスワード未入力
        if (pass == null || pass.isBlank()) {

            model.addAttribute(
                    "errorMsg",
                    "パスワードを入力してください");

            model.addAttribute(
                    "name",
                    name);

            return "index";
        }

        // 入力値をMemberへ格納
        Member member = new Member();

        member.setMemberId(name);
        member.setPassword(pass);

        // DAOでログイン認証
        Member loginMember =
                loginListDAO.findByLogin(member);

        // ログイン失敗
        if (loginMember == null) {

            model.addAttribute(
                    "errorMsg",
                    "会員IDまたはパスワードが正しくありません");

            model.addAttribute(
                    "name",
                    name);

            return "index";
        }

        /*
         * ログイン成功
         * セッションへログイン情報を保存
         */
        session.setAttribute(
                "loginMember",
                loginMember);

        // メイン画面へ
        return "main";
    }
    
    /**
     * メイン画面表示
     */
    @GetMapping("/main")
    public String showMain(HttpSession session) {

        // 未ログインならログイン画面へ戻す
        if (session.getAttribute("loginMember") == null) {
            return "redirect:/";
        }

        return "main";
    }

    /**
     * ログアウト
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/";
    }

}