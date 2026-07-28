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

    @Autowired
    private LoginListDAO loginListDAO;

    /**
     * ログイン画面を表示します。
     */
    @GetMapping({"/", "/index"})
    public String showLogin() {

        return "index";
    }

    /**
     * ログイン処理を行います。
     */
    @PostMapping("/Login")
    public String login(

            @RequestParam(name = "name")
            String name,

            @RequestParam(name = "pass")
            String pass,

            HttpSession session,

            Model model) {

        // 会員IDが未入力の場合
        if (name == null || name.isBlank()) {

            model.addAttribute(
                    "errorMsg",
                    "会員IDを入力してください");

            return "index";
        }

        // パスワードが未入力の場合
        if (pass == null || pass.isBlank()) {

            model.addAttribute(
                    "errorMsg",
                    "パスワードを入力してください");

            model.addAttribute(
                    "name",
                    name);

            return "index";
        }

        // 入力されたログイン情報をMemberへ格納
        Member member = new Member();

        member.setMemberId(name);
        member.setPassword(pass);

        // DBから会員情報を検索
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
        *
        * main.jsp・在庫管理ではloginUserを使用し、
        * 既存の管理者機能ではloginMemberを使用しているため、
        * 移行期間中は両方の名前で保存します。
        */
        session.setAttribute(
                "loginUser",
                loginMember);

        session.setAttribute(
        		"loginMember",
        		loginMember);
        
        return "main";
    }

    /**
     * メイン画面を表示します。
     */
    @GetMapping("/main")
    public String showMain(HttpSession session) {

        // 未ログインならログイン画面へ戻す
        if (session.getAttribute("loginUser") == null) {

            return "redirect:/";
        }

        return "main";
    }

    /**
     * ログアウト処理を行います。
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {

        // セッション内のログイン情報をすべて破棄
        session.invalidate();

        return "redirect:/";
    }
}