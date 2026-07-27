package com.example.demo.service;

import java.time.LocalDate;
import java.util.Set;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.example.demo.dao.NewRegistrationDAO;
import com.example.demo.model.Member;

import lombok.RequiredArgsConstructor;

/**
 * 新規会員登録に必要な確認を行うServiceです。
 *
 * 主な処理
 * ・入力値の確認
 * ・会員IDの重複確認
 * ・一般会員の権限設定
 * ・DAOへの登録依頼
 */
@Service
@RequiredArgsConstructor
public class NewRegistrationService {

    /*
     * 新規登録した会員へ設定する権限です。
     *
     * 利用者が画面からADMINを指定できないように、
     * Service側で必ずUSERを設定します。
     */
    private static final String DEFAULT_ROLE = "USER";

    /*
     * 会員IDは半角英数字4～20文字です。
     */
    private static final Pattern MEMBER_ID_PATTERN =
            Pattern.compile("[A-Za-z0-9]{4,20}");

    /*
     * パスワードは英字と数字を含む8～64文字です。
     */
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile(
                    "^(?=.*[A-Za-z])(?=.*[0-9]).{8,64}$");

    /*
     * 郵便番号は123-4567または1234567の形式です。
     */
    private static final Pattern POSTAL_CODE_PATTERN =
            Pattern.compile("\\d{3}-?\\d{4}");

    /*
     * 画面から受け付ける支払方法です。
     */
    private static final Set<String> PAYMENT_METHODS =
            Set.of(
                    "credit",
                    "bank",
                    "cash_on_delivery",
                    "convenience_store");

    private final NewRegistrationDAO newRegistrationDAO;

    /**
     * 新しい会員を登録します。
     *
     * @param member 登録する会員情報
     */
    public void execute(Member member) {

        /*
         * DBへ登録する前に入力内容を確認します。
         */
        validate(member);

        /*
         * 同じ会員IDの重複登録を防ぎます。
         */
        if (newRegistrationDAO.existsByMemberId(
                member.getMemberId())) {

            throw new IllegalArgumentException(
                    "この会員IDはすでに使用されています。");
        }

        /*
         * 新規登録者の権限は必ずUSERにします。
         *
         * roleを画面から受け取らないことで、
         * 利用者がADMINを指定することを防ぎます。
         */
        member.setRole(DEFAULT_ROLE);

        /*
         * DAOを使ってmembersテーブルへ登録します。
         */
        boolean created =
                newRegistrationDAO.create(member);

        if (!created) {
            throw new IllegalStateException(
                    "会員登録に失敗しました。");
        }
    }

    /**
     * 会員情報の入力内容を確認します。
     *
     * @param member 会員情報
     */
    private void validate(Member member) {

        if (member == null) {
            throw new IllegalArgumentException(
                    "会員情報が入力されていません。");
        }

        if (isBlank(member.getMemberId())) {
            throw new IllegalArgumentException(
                    "会員IDを入力してください。");
        }

        if (!MEMBER_ID_PATTERN
                .matcher(member.getMemberId())
                .matches()) {

            throw new IllegalArgumentException(
                    "会員IDは半角英数字4～20文字で入力してください。");
        }

        if (isBlank(member.getPassword())) {
            throw new IllegalArgumentException(
                    "パスワードを入力してください。");
        }

        if (!PASSWORD_PATTERN
                .matcher(member.getPassword())
                .matches()) {

            throw new IllegalArgumentException(
                    "パスワードは英字と数字を含む8文字以上で入力してください。");
        }

        if (isBlank(member.getMemberName())) {
            throw new IllegalArgumentException(
                    "氏名を入力してください。");
        }

        if (isBlank(member.getPostalCode())) {
            throw new IllegalArgumentException(
                    "郵便番号を入力してください。");
        }

        if (!POSTAL_CODE_PATTERN
                .matcher(member.getPostalCode())
                .matches()) {

            throw new IllegalArgumentException(
                    "郵便番号を正しい形式で入力してください。");
        }

        if (isBlank(member.getAddress())) {
            throw new IllegalArgumentException(
                    "住所を入力してください。");
        }

        if (isBlank(member.getPhoneNumber())) {
            throw new IllegalArgumentException(
                    "電話番号を入力してください。");
        }

        if (member.getBirthDate() == null) {
            throw new IllegalArgumentException(
                    "生年月日を入力してください。");
        }

        /*
         * 未来の日付を生年月日として登録できないようにします。
         */
        if (member.getBirthDate()
                .isAfter(LocalDate.now())) {

            throw new IllegalArgumentException(
                    "生年月日に未来の日付は指定できません。");
        }

        if (isBlank(member.getEmail())) {
            throw new IllegalArgumentException(
                    "メールアドレスを入力してください。");
        }

        if (isBlank(member.getPaymentMethod())) {
            throw new IllegalArgumentException(
                    "支払方法を選択してください。");
        }

        if (!PAYMENT_METHODS.contains(
                member.getPaymentMethod())) {

            throw new IllegalArgumentException(
                    "正しい支払方法を選択してください。");
        }
    }

    /**
     * nullまたは空文字か確認します。
     *
     * @param value 確認する文字列
     * @return 未入力の場合はtrue
     */
    private boolean isBlank(String value) {

        return value == null || value.isBlank();
    }
}