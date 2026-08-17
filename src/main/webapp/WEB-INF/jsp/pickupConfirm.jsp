<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head><link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/favicon.ico">
<meta charset="UTF-8">
<title>予約内容確認</title>
</head>
<body>

<h1>予約内容確認</h1>

<p>
    お名前：
    ${customerName}
</p>

<p>
    商品ID：
    ${productId}
</p>

<p>
    数量：
    ${quantity}
</p>

<p>
    受取希望日：
    ${reservationDate}
</p>

<p>
    受取希望時間：
    ${reservationTime}
</p>

<p>
    メールアドレス：
    ${email}
</p>

<p>
    電話番号：
    ${phone}
</p>

<hr>

<p>この内容で予約しますか？</p>

<form action="${pageContext.request.contextPath}/pickup/complete"
      method="post">

    <input type="hidden"
           name="customerName"
           value="${customerName}">

    <input type="hidden"
           name="productId"
           value="${productId}">

    <input type="hidden"
           name="reservationDate"
           value="${reservationDate}">

    <input type="hidden"
           name="reservationTime"
           value="${reservationTime}">

    <input type="hidden"
           name="email"
           value="${email}">

    <input type="hidden"
           name="phone"
           value="${phone}">

    <button type="submit">
        予約を確定する
    </button>

</form>

</body>
</html>