package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LogoutController{
	
	/*URLを打ち込んだ際にログアウトされる可能性がある為、
	対策としてGetMappingではなくPostMappingにしてます。*/
	@PostMapping("/Logout")
	public String logout(HttpSession session) {
	    session.invalidate();
//	    一度logoutResult.jspをはさむ為、コメントアウトしてます。
//	    return "redirect:/";
	    
	    return "logoutResult";
	}
}
