package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dao.MemberEditDAO;
import com.example.demo.model.Member;

@Controller
public class AdminMemberController {

	@Autowired
	private MemberEditDAO memberEditDAO;

	/**
	 * 指定した会員を管理者に変更する
	 */
	@PostMapping("/admin/member/role")
	public String updateRole(
			@RequestParam("memberId") String memberId,

			HttpSession session) {

		Member loginMember = (Member) session.getAttribute("loginMember");

		// 未ログイン
		if (loginMember == null) {
			return "redirect:/";
		}

		// 管理者以外
		if (!"admin".equals(loginMember.getRole())) {
			return "redirect:/main";
		}

		memberEditDAO.updateRole(
				memberId,
				"admin");

		return "redirect:/admin";
	}
}