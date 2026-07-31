package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.dao.MemberEditDAO;
import com.example.demo.model.Member;

@Service
public class MemberEditService {

    private final MemberEditDAO memberEditDAO;

    public MemberEditService(MemberEditDAO memberEditDAO) {
        this.memberEditDAO = memberEditDAO;
    }

    /*
     * 会員IDを使って、現在の会員情報を取得する
     */
    public Member findById(String memberId) {

        if (memberId == null || memberId.isBlank()) {
            return null;
        }

        return memberEditDAO.findById(memberId);
    }

    /*
     * 会員情報の入力チェック
     * 問題がなければnull、
     * 問題があればエラーメッセージを返す
     */
    
    public String validate(Member member) {

        if (member == null) {
            return "入力内容を確認してください";
        }

        if (member.getMemberName() == null
                || member.getMemberName().isBlank()) {
            return "氏名を入力してください";
        }

        if (member.getMemberName().length() > 50) {
            return "氏名は50文字以内で入力してください";
        }

        if (member.getPostalCode() == null
                || member.getPostalCode().isBlank()) {
            return "郵便番号を入力してください";
        }

     // ハイフンを取り除いて7桁の数字になっているかチェック
        String cleanPostalCode = member.getPostalCode().replace("-", "");
        if (!cleanPostalCode.matches("\\d{7}")) {
            return "郵便番号は7桁の数字（ハイフン可）で入力してください";
        }

        if (member.getAddress() == null
                || member.getAddress().isBlank()) {
            return "住所を入力してください";
        }

        if (member.getPhoneNumber() == null
                || member.getPhoneNumber().isBlank()) {
            return "電話番号を入力してください";
        }

        // ★ハイフンを取り除いて10〜11桁の数字になっているかチェック
        String cleanPhoneNumber = member.getPhoneNumber().replace("-", "");
        if (!cleanPhoneNumber.matches("\\d{10,11}")) {
            return "電話番号は10桁または11桁の数字（ハイフン可）で入力してください";
        }
        
        if (member.getBirthDate() == null) {
            return "生年月日を入力してください";
        }

        if (member.getBirthDate()
                .isAfter(java.time.LocalDate.now())) {
            return "生年月日に未来の日付は指定できません";
        }

        if (member.getEmail() == null
                || member.getEmail().isBlank()) {
            return "メールアドレスを入力してください";
        }

        if (!member.getEmail().contains("@")) {
            return "メールアドレスの形式を確認してください";
        }

        if (member.getPaymentMethod() == null
                || member.getPaymentMethod().isBlank()) {
            return "支払方法を選択してください";
        }

        return null;
    }
    
    
    /*
     * 会員情報を更新する
     */
    public boolean update(Member member) {

    	    if (member == null) {
    	        return false;
    	    }

    	    if (member.getMemberId() == null
    	            || member.getMemberId().isBlank()) {
    	        return false;
    	    }

    	    if (validate(member) != null) {
    	        return false;
    	    }

    	    return memberEditDAO.update(member);
    	}
}