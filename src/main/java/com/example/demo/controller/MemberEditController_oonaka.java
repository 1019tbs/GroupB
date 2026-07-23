package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.Member;
import com.example.demo.service.MemberEditService;

@Controller
public class MemberEditController_oonaka {

    private final MemberEditService memberEditService;

    public MemberEditController_oonaka(
            MemberEditService memberEditService) {

        this.memberEditService = memberEditService;
    }

    /*
     * 会員情報変更画面を表示
     */
    @GetMapping("/member/edit_oonaka")
    public String showEdit(
            HttpSession session,
            Model model) {

        Member loginMember =
                (Member) session.getAttribute("loginMember");

        if (loginMember == null) {
            return "redirect:/";
        }

        /*
         * ログイン中の会員IDを使って、
         * DBから現在の会員情報を取得
         */
        Member member =
                memberEditService.findById(
                        loginMember.getMemberId());

        if (member == null) {

            model.addAttribute(
                    "errorMsg",
                    "会員情報を取得できませんでした");

            return "main";
        }

        model.addAttribute(
                "member",
                member);

        return "MemberEdit_oonaka";
    }

    /*
     * 変更画面から送信された内容を受け取り、
     * DBを更新する
     */
    @PostMapping("/member/edit_oonaka")
    public String update(
            Member member,
            HttpSession session,
            Model model) {

        Member loginMember =
                (Member) session.getAttribute("loginMember");

        if (loginMember == null) {
            return "redirect:/";
        }

        /*
         * 会員IDは変更不可。
         * 画面から送信された値は使わず、
         * Sessionの会員IDを設定する
         */
        member.setMemberId(
                loginMember.getMemberId());

        /*
         * roleは画面から送信しないため、
         * ログイン中の会員情報を引き継ぐ
         */
        member.setRole(
                loginMember.getRole());

        /*
         * Serviceに更新を依頼
         */

        String errorMsg =
                memberEditService.validate(member);

        if (errorMsg != null) {

            model.addAttribute(
                    "errorMsg",
                    errorMsg);

            model.addAttribute(
                    "member",
                    member);

            return "MemberEdit_oonaka";
        }

        boolean result =
                memberEditService.update(member);

        if (!result) {

            model.addAttribute(
                    "errorMsg",
                    "会員情報の更新に失敗しました");

            model.addAttribute(
                    "member",
                    member);

            return "MemberEdit_oonaka";
        }

        /*
         * Session内の会員情報も更新する
         *
         * main.jspなどで表示する氏名や権限が
         * 古いまま残るのを防ぐ
         */
        Member updatedMember =
                memberEditService.findById(
                        loginMember.getMemberId());

        session.setAttribute(
                "loginMember",
                updatedMember);

        model.addAttribute(
                "member",
                updatedMember);

        return "memberEditComplete_oonaka";
    }
}