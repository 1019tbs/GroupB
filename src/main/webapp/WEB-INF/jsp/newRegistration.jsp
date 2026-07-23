<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>新規会員登録 | Honey Bloom</title>
<!-- css -->
<link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/style.css">

<style>
body {
    margin: 0;
    background-color: #fffaf2;
    color: #4a321f;
    font-family:
        "Yu Gothic",
        "Meiryo",
        sans-serif;
}

.container {
    width: min(680px, 90%);
    margin: 40px auto;
    padding: 30px 40px;
    background-color: #ffffff;
    border: 1px solid #e6cfaa;
    border-radius: 12px;
    box-sizing: border-box;
    box-shadow: 0 4px 12px rgba(100, 70, 30, 0.12);
}

h1 {
    margin-top: 0;
    color: #783f04;
    text-align: center;
}

.description {
    margin-bottom: 25px;
    text-align: center;
    color: #6c5845;
}

.required {
    margin-left: 5px;
    padding: 2px 6px;
    background-color: #b94a48;
    color: #ffffff;
    border-radius: 4px;
    font-size: 0.75rem;
}

.form-group {
    margin-bottom: 20px;
}

label {
    display: block;
    margin-bottom: 6px;
    font-weight: bold;
}

input,
select {
    width: 100%;
    padding: 10px;
    border: 1px solid #cbb99f;
    border-radius: 6px;
    box-sizing: border-box;
    font-size: 1rem;
}

input:focus,
select:focus {
    border-color: #b67a33;
    outline: 2px solid rgba(182, 122, 51, 0.18);
}

.postal-row {
    display: flex;
    gap: 10px;
}

.postal-row input {
    flex: 1;
}

.postal-row button {
    white-space: nowrap;
}

.help-text {
    display: block;
    margin-top: 5px;
    color: #75695e;
    font-size: 0.82rem;
}

.error-message {
    margin-bottom: 20px;
    padding: 12px;
    background-color: #fff0f0;
    color: #b00020;
    border: 1px solid #e2a5a5;
    border-radius: 6px;
}

.button-area {
    display: flex;
    justify-content: center;
    gap: 15px;
    margin-top: 30px;
}

button,
.button-link {
    min-width: 140px;
    padding: 11px 18px;
    border-radius: 6px;
    box-sizing: border-box;
    cursor: pointer;
    font-size: 1rem;
    text-align: center;
    text-decoration: none;
}

.register-button {
    border: none;
    background-color: #b66a21;
    color: #ffffff;
}

.register-button:hover {
    background-color: #914f13;
}

.address-button {
    width: auto;
    border: 1px solid #9b6d36;
    background-color: #fff7e8;
    color: #704515;
}

.address-button:hover {
    background-color: #f4e3c4;
}

.back-button {
    border: 1px solid #9b8975;
    background-color: #ffffff;
    color: #4a321f;
}
</style>
</head>

<body class= "newRegistration">
<!-- 背景画像 -->
<img src="${pageContext.request.contextPath}/images/registerTop.png"
     class="registerTop"
     alt="登録画面トップ">

<img src="${pageContext.request.contextPath}/images/registerLeft.png"
     class="registerLeft"
     alt="登録画面左">

<img src="${pageContext.request.contextPath}/images/registerRight.png"
     class="registerRight"
     alt="登録画面右">

<div class="container">

    <h1>新規会員登録</h1>

    <p class="description">
        必要事項を入力して「登録する」を押してください。
    </p>

<%-- Controllerからエラーメッセージが渡された場合 --%>
<% if (request.getAttribute("errorMsg") != null) {  %>
    <div class="error-message">
        <%= request.getAttribute("errorMsg") %>
    </div> 
<% } %>

<form 
    action="${pageContext.request.contextPath}/registration"
    method="post">
    
    <div class="form-group">
        <label for="memberId">
            会員ID
            <span class="required">必須</span>
        </label>
        
        <input 
            type="text"
            id="memberId"
            name="memberId"
            value="${memberId}"
            minlength="4" 
            maxlength="20"
            pattern="[A-Za-z0-9]+"
            title="会員IDは半角英数字で入力してください"
            autocomplete="username"
            required>
            
         <span class="help-text">
             半角英数字4～20文字で入力してください。
             会員IDは登録後に変更できません。
         </span>
     </div>
     
      <div class="form-group">
            <label for="password">
                パスワード
                <span class="required">必須</span>
            </label>

            <input
                type="password"
                id="password"
                name="password"
                minlength="8"
                maxlength="64"
                pattern="(?=.*[A-Za-z])(?=.*[0-9]).{8,64}"
                title="英字と数字を含む8文字以上で入力してください"
                autocomplete="new-password"
                required>

            <span class="help-text">
                英字と数字を含む8文字以上で入力してください。
            </span>
        </div>

        <div class="form-group">
            <label for="passwordConfirm">
                パスワード（確認）
                <span class="required">必須</span>
            </label>

            <input
                type="password"
                id="passwordConfirm"
                name="passwordConfirm"
                minlength="8"
                maxlength="64"
                autocomplete="new-password"
                required>
        </div>

        <div class="form-group">
            <label for="memberName">
                氏名
                <span class="required">必須</span>
            </label>

            <input
                type="text"
                id="memberName"
                name="memberName"
                value="${memberName}"
                maxlength="50"
                autocomplete="name"
                required>
        </div>

        <div class="form-group">
            <label for="postalCode">
                郵便番号
                <span class="required">必須</span>
            </label>

            <div class="postal-row">
                <input
                    type="text"
                    id="postalCode"
                    name="postalCode"
                    value="${postalCode}"
                    inputmode="numeric"
                    maxlength="8"
                    pattern="\d{3}-?\d{4}"
                    placeholder="123-4567"
                    title="郵便番号は123-4567の形式で入力してください"
                    autocomplete="postal-code"
                    required>

                <button
                    type="button"
                    class="address-button"
                    id="addressSearchButton">
                    住所検索
                </button>
            </div>

            <span class="help-text">
                ハイフンあり、なしのどちらでも入力できます。
            </span>
        </div>

        <div class="form-group">
            <label for="address">
                住所
                <span class="required">必須</span>
            </label>

            <input
                type="text"
                id="address"
                name="address"
                value="${address}"
                maxlength="150"
                autocomplete="street-address"
                required>
        </div>

        <div class="form-group">
            <label for="phoneNumber">
                電話番号
                <span class="required">必須</span>
            </label>

            <input
                type="tel"
                id="phoneNumber"
                name="phoneNumber"
                value="${phoneNumber}"
                inputmode="tel"
                maxlength="15"
                pattern="0\d{1,4}-?\d{1,4}-?\d{3,4}"
                placeholder="090-1234-5678"
                title="電話番号を正しい形式で入力してください"
                autocomplete="tel"
                required>
        </div>

        <div class="form-group">
            <label for="birthDate">
                生年月日
                <span class="required">必須</span>
            </label>

            <input
                type="date"
                id="birthDate"
                name="birthDate"
                value="${birthDate}"
                autocomplete="bday"
                required>
        </div>

        <div class="form-group">
            <label for="email">
                メールアドレス
                <span class="required">必須</span>
            </label>

            <input
                type="email"
                id="email"
                name="email"
                value="${email}"
                maxlength="254"
                placeholder="example@example.com"
                autocomplete="email"
                required>
        </div>

        <div class="form-group">
            <label for="paymentMethod">
                支払方法
                <span class="required">必須</span>
            </label>

            <select
                id="paymentMethod"
                name="paymentMethod"
                required>

                <option value="">
                    選択してください
                </option>

                <option value="credit">
                    クレジットカード
                </option>

                <option value="bank">
                    銀行振込
                </option>

                <option value="cash_on_delivery">
                    代金引換
                </option>

                <option value="convenience_store">
                    コンビニ払い
                </option>
            </select>
        </div>

        <div class="button-area">

            <a
                href="${pageContext.request.contextPath}/"
                class="button-link back-button">
                戻る
            </a>

            <button
                type="submit"
                class="register-button">
                登録する
            </button>

        </div>
        
     </form>
</div>

<script>
"use strict";

const form = document.querySelector("form");
const password = document.getElementById("password");
const passwordConfirm =
        document.getElementById("passwordConfirm");

form.addEventListener("submit", function(event) {
    passwordConfirm.setCustomValidity("");

    if (password.value !== passwordConfirm.value) {
        event.preventDefault();
        passwordConfirm.setCustomValidity(
                "確認用パスワードが一致していません");
        passwordConfirm.reportValidity();
    }
});

passwordConfirm.addEventListener("input", function() {
    if (password.value === passwordConfirm.value) {
        passwordConfirm.setCustomValidity("");
    }
});

document.getElementById("addressSearchButton")
        .addEventListener("click", function() {

    const postalCode =
             document.getElementById("postalCode").value;

    const normalizedPostalCode =
             postalCode.replace(/-/g, "");

    if (!/^\d{7}$/.test(normalizedPostalCode)) {
        alert("郵便番号を7桁で入力してください。");
        return;
}

 /*
     * 住所検索は、Controller側に検索処理が完成したら接続します。
     *
     * 例：
     * fetch(
     *   contextPath
     *   + "/address/search?postalCode="
     *   + normalizedPostalCode
     * )
     */

    alert(
        "郵便番号から住所を検索する処理は"
        + "Controller完成後に接続します。");
});
</script>


</body>
</html>