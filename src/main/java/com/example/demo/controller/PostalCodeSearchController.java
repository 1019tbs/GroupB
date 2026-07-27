package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.PostalCodeSearch;
import com.example.demo.service.PostPostalCodeService;

@Controller
public class PostalCodeSearchController {

	@Autowired
	private PostPostalCodeService postPostalCodeService;

	// JavaScriptなし（フォーム送信）で住所検索ボタンが押された時の処理
	@PostMapping("/search-address")
	public String searchAddress(
			@RequestParam(required = false) String postalCode,
			Model model) {

		// 作成した Service を呼び出して住所を検索（7桁・8桁どちらもService内で自動判定・整形）
		PostalCodeSearch result = postPostalCodeService.execute(postalCode);

		// 入力された郵便番号をそのまま画面に戻す（検索後に消えないようにするため）
		model.addAttribute("postalCode", postalCode);

		if (result != null) {
			// 都道府県・市区町村・町域を結合して画面に渡す
            String fullAddress = result.getPrefecture() + result.getCity() + result.getTown();
            model.addAttribute("address", fullAddress);
        } else {
            model.addAttribute("errorMessage", "該当する住所が見つかりませんでした。");
        }

        // 会員登録画面（register.jsp）を再表示
        return "newRegistration";
    }
}