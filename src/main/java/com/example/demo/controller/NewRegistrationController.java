package com.example.demo.controller;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Member;
import com.example.demo.service.NewRegistrationService;

import lombok.RequiredArgsConstructor;

/**
 * 新規会員登録画面を担当するControllerです。
 *
 * 主な処理
 * ・登録画面を表示する
 * ・画面から入力値を受け取る
 * ・生年月日をLocalDateへ変換する
 * ・Serviceへ会員登録を依頼する
 */
@Controller
@RequiredArgsConstructor
public class NewRegistrationController {

    private final NewRegistrationService
            newRegistrationService;

    /**
     * 新規会員登録画面を表示します。
     *
     * URL：
     * GET /registration
     *
     * @return newRegistration.jsp
     */
    @GetMapping("/registration")
    public String showRegistration() {

        return "newRegistration";
    }

    /**
     * 新規会員を登録します。
     *
     * URL：
     * POST /registration
     */
    @PostMapping("/registration")
    public String register(
            @RequestParam("memberId")
            String memberId,

            @RequestParam("password")
            String password,

            @RequestParam("passwordConfirm")
            String passwordConfirm,

            @RequestParam("memberName")
            String memberName,

            @RequestParam("postalCode")
            String postalCode,

            @RequestParam("address")
            String address,

            @RequestParam("phoneNumber")
            String phoneNumber,

            @RequestParam("birthDate")
            String birthDateText,

            @RequestParam("email")
            String email,

            @RequestParam("paymentMethod")
            String paymentMethod,

            Model model) {

        /*
         * エラーで登録画面へ戻った場合に、
         * 入力内容を再表示できるよう保存します。
         *
         * パスワードは安全のため再表示しません。
         */
        keepInputValues(
                model,
                memberId,
                memberName,
                postalCode,
                address,
                phoneNumber,
                birthDateText,
                email,
                paymentMethod);

        /*
         * パスワードと確認用パスワードが
         * 一致しているか確認します。
         */
        if (!password.equals(passwordConfirm)) {

            model.addAttribute(
                    "errorMsg",
                    "パスワードが一致していません。");

            return "newRegistration";
        }

        try {
            /*
             * 画面から受け取った生年月日は文字列です。
             *
             * MemberのbirthDateはLocalDate型なので、
             * LocalDateへ変換します。
             */
            LocalDate birthDate =
                    LocalDate.parse(birthDateText);

            /*
             * 画面の入力値からMemberを作成します。
             *
             * roleはServiceでUSERを設定するため、
             * Controllerではnullにしておきます。
             */
            Member member = new Member();

            member.setMemberId(memberId.trim());
            member.setPassword(password);
            member.setMemberName(memberName.trim());
            member.setPostalCode(postalCode.trim());
            member.setAddress(address.trim());
            member.setPhoneNumber(phoneNumber.trim());
            member.setBirthDate(birthDate);
            member.setEmail(email.trim());
            member.setPaymentMethod(paymentMethod);
            member.setRole(null);

            /*
             * Serviceへ会員登録を依頼します。
             */
            newRegistrationService.execute(member);

            /*
             * 登録結果画面で会員情報を使用できるよう、
             * MemberをModelへ保存します。
             */
            model.addAttribute("member", member);

            return "registrationResult";

        } catch (DateTimeParseException e) {

            model.addAttribute(
                    "errorMsg",
                    "生年月日を正しく入力してください。");

            return "newRegistration";

        } catch (IllegalArgumentException |
                 IllegalStateException e) {

            model.addAttribute(
                    "errorMsg",
                    e.getMessage());

            return "newRegistration";
        }
    }

    /**
     * エラー時に入力内容を再表示するため、
     * Modelへ値を保存します。
     *
     * パスワードは保存しません。
     */
    private void keepInputValues(
            Model model,
            String memberId,
            String memberName,
            String postalCode,
            String address,
            String phoneNumber,
            String birthDate,
            String email,
            String paymentMethod) {

        model.addAttribute(
                "memberId",
                memberId);

        model.addAttribute(
                "memberName",
                memberName);

        model.addAttribute(
                "postalCode",
                postalCode);

        model.addAttribute(
                "address",
                address);

        model.addAttribute(
                "phoneNumber",
                phoneNumber);

        model.addAttribute(
                "birthDate",
                birthDate);

        model.addAttribute(
                "email",
                email);

        model.addAttribute(
                "paymentMethod",
                paymentMethod);
    }
}