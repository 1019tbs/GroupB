<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>会員情報変更</title>
</head>
<body>

<h1>会員情報変更</h1>

<c:if test="${not empty errorMsg}">
    <p style="color:red;">
        <c:out value="${errorMsg}" />
    </p>
</c:if>

<form action="${pageContext.request.contextPath}/member/edit_oonaka"
      method="post">

    <p>
        会員ID：
        <c:out value="${member.memberId}" />
    </p>

    <input type="hidden"
           name="memberId"
           value="${member.memberId}">

<!--
    <p>
        パスワード：
        <input type="password"
               name="password"
               value="${member.password}">
    </p>
-->

    <p>
        氏名：
        <input type="text"
               name="memberName"
               value="${member.memberName}">
    </p>

    <p>
        郵便番号：
        <input type="text"
               name="postalCode"
               value="${member.postalCode}">
    </p>

    <p>
        住所：
        <input type="text"
               name="address"
               value="${member.address}">
    </p>

    <p>
        電話番号：
        <input type="text"
               name="phoneNumber"
               value="${member.phoneNumber}">
    </p>

    <p>
        生年月日：
        <input type="date"
               name="birthDate"
               value="${member.birthDate}">
    </p>

    <p>
        メールアドレス：
        <input type="email"
               name="email"
               value="${member.email}">
    </p>

    <p>
        支払方法：
        <select name="paymentMethod">
            <option value="CREDIT"
                <c:if test="${member.paymentMethod == 'CREDIT'}">
                    selected
                </c:if>>
                クレジットカード
            </option>

            <option value="COD"
                <c:if test="${member.paymentMethod == 'COD'}">
                    selected
                </c:if>>
                代金引換
            </option>

            <option value="BANK"
                <c:if test="${member.paymentMethod == 'BANK'}">
                    selected
                </c:if>>
                銀行振込
            </option>
        </select>
    </p>

    <button type="submit">
        変更する
    </button>

</form>

</body>
</html>