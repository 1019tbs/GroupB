package com.example.demo.controller;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dao.FormContactDAO;
import com.example.demo.model.FormContact;
import com.example.demo.model.Member;

@Controller
public class AdminFormContactController {

    @Autowired
    private FormContactDAO formContactDAO;

    /**
     * 管理者用のお問い合わせ一覧画面を表示
     */
    @GetMapping("/admin/contact")
    public String showContactList(
            HttpSession session,
            Model model) {

        Member loginMember =
                (Member) session.getAttribute("loginMember");

        // 未ログインの場合
        if (loginMember == null) {
            return "redirect:/";
        }

        // 管理者以外の場合
/*        if (!"admin".equals(loginMember.getRole())) {
            return "redirect:/main";
        }
*/
        List<FormContact> contactList =
                formContactDAO.findAll();

        model.addAttribute(
                "contactList",
                contactList);

        return "adminContact";
    }
    
    //お問い合わせを対応済みに変更する機能
    @PostMapping("/admin/contact/status")
    public String updateStatus(

            @RequestParam("contactId")
            long contactId,

            HttpSession session) {

        Member loginMember =
                (Member) session.getAttribute("loginMember");
    	
    	//未ログイン
    	if(loginMember == null) {
    		return "redirect:/";
    	}
    	//管理者以外
    	if(!"admin".equals(loginMember.getRole())) {
    		return "redirect:/main";
    	}
    	
    	//status = 1(対応済み)
    	formContactDAO.updateStatus(contactId, 1);
    	return "redirect:/admin/contact";
    }
}