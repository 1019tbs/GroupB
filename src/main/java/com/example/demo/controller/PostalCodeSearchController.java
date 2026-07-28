package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.PostalCodeSearch;
import com.example.demo.service.PostPostalCodeService;

@Controller
public class PostalCodeSearchController {

	@Autowired
	private PostPostalCodeService postPostalCodeService;

	// JavaScript(Fetch API)からの非同期リクエストを受け取る処理
	@PostMapping("/search-address")
	// 戻り値（PostalCodeSearch）が自動的にJSON形式に変換されてJavaScriptへ返ります
	@ResponseBody     
	public PostalCodeSearch searchAddress(@RequestParam(required = false) String postalCode) {

		// Service を呼び出して住所を検索（見つからなければ null が返る）
		PostalCodeSearch result = postPostalCodeService.execute(postalCode);
		
		// 見つからなかった（null）場合、空のオブジェクトを返して JSON エラーを防ぐ
        if (result == null) {
            return new PostalCodeSearch();
        }

        // （住所データまたはnull）をそのまま返す
        return result;
    }
}