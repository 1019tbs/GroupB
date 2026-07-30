package com.example.demo.controller;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dao.MemberEditDAO;
import com.example.demo.model.Member;

@Controller
public class AdminMemberController {

    @Autowired
    private MemberEditDAO memberEditDAO;

    /**
     * 会員管理画面を表示する
     */
    @GetMapping("/admin/member")
    public String showMemberList(
            HttpSession session,
            Model model) {

        Member loginMember =
                (Member) session.getAttribute("loginMember");

        // 未ログインの場合
        if (loginMember == null) {
            return "redirect:/";
        }

        // 管理者以外の場合
//        if (!"admin".equals(loginMember.getRole())) {
//            return "redirect:/main";
//        }

        // 会員一覧を取得
        List<Member> memberList =
                memberEditDAO.findAll();

        // JSPへ会員一覧を渡す
        model.addAttribute(
                "memberList",
                memberList);

        return "adminMember";
    }

    /**
     * 会員の権限を変更する
     */
    @PostMapping("/admin/member/role")
    public String updateRole(
            @RequestParam("memberId")
            String memberId,

            @RequestParam("role")
            String role,

            HttpSession session) {

        Member loginMember =
                (Member) session.getAttribute("loginMember");

        // 未ログインの場合
        if (loginMember == null) {
            return "redirect:/";
        }

        // 管理者以外の場合
//        if (!"admin".equals(loginMember.getRole())) {
//            return "redirect:/main";
//        }

        // 不正なroleが送られていないか確認
        if (!"admin".equals(role)
                && !"user".equals(role)) {

            return "redirect:/admin/member";
        }

        // 会員の権限を更新
        memberEditDAO.updateRole(
                memberId,
                role);

        // 会員管理画面を再表示
        return "redirect:/admin/member";
    }
}