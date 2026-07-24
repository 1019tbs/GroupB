//package com.example.demo.controller;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import com.example.demo.model.Member;
//import com.example.demo.service.NewRegistrationService;
//
//import lombok.RequiredArgsConstructor;
//
//@Controller
//@RequiredArgsConstructor
//public class NewRegistrationController {
//
//    private final NewRegistrationService newRegistrationService;
//
//    @GetMapping("/registration")
//    public String showRegistration() {
//        return "newRegistration";
//    }
//
//    @PostMapping("/registration")
//    public String register(
//            @RequestParam String memberId,
//            @RequestParam String password,
//            @RequestParam String passwordConfirm,
//            @RequestParam String memberName,
//            @RequestParam String postalCode,
//            @RequestParam String address,
//            @RequestParam String phoneNumber,
//            @RequestParam String birthDate,
//            @RequestParam String email,
//            @RequestParam String paymentMethod,
//            Model model) {
//
//        // パスワード確認
//        if (!password.equals(passwordConfirm)) {
//            model.addAttribute(
//                    "errorMsg",
//                    "パスワードが一致していません");
//
//            return "newRegistration";
//        }
//
//        // Memberに値をセット
//        Member member = new Member();
//
//        member.setMemberId(memberId);
//        member.setPassword(password);
//        member.setMemberName(memberName);
//        member.setPostalCode(postalCode);
//        member.setAddress(address);
//        member.setPhoneNumber(phoneNumber);
//        member.setBirthDate(birthDate);
//        member.setEmail(email);
//        member.setPaymentMethod(paymentMethod);
//
//        // Service実行
//        boolean result =
//                newRegistrationService.execute(member);
//
//        if (!result) {
//            model.addAttribute(
//                    "errorMsg",
//                    "会員登録に失敗しました");
//
//            return "newRegistration";
//        }
//
//        return "registrationResult";
//    }
//}