package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.PostalCodeDAO;
import com.example.demo.model.PostalCodeSearch;

@Service
public class PostPostalCodeService {

	// @Autowired で自動注入する
@Autowired
	private PostalCodeDAO postalCodeDAO;

	// 郵便番号（7桁またはハイフン付き8桁）を受け取り、住所を検索して返す処理
	public PostalCodeSearch execute(String postalCode) {
		// 1. null や 空文字 のチェック
		if (postalCode == null || postalCode.trim().isEmpty()) {
			return null;
		}

		// 余計な前後の空白を削除
		String input = postalCode.trim();

		// 2. 入力された文字数が「7文字」または「8文字」か確認する
		// (7文字未満や9文字以上などの不正な入力を弾く)
		if (input.length() != 7 && input.length() != 8) {
			return null;
		}

		// 3. ハイフン（"-"）を取り除いて数字だけの文字列にする
		String cleanCode = input.replace("-", "");

		// 4. ハイフンを除去した結果が「ちょうど7桁」になっているか確認
		// ("12345678" のようなハイフンなし8文字などはここで弾かれる)
		if (cleanCode.matches("\\d{7}")) {
			return null;
		}
			// 5. 7桁に整えた郵便番号で DAO を呼び出し、検索結果を返す
			return postalCodeDAO.findByPostalCode(cleanCode);
		
	}
}	