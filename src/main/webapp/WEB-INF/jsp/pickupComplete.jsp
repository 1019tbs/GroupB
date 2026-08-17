<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head><link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/favicon.ico">
<meta charset="UTF-8">
<title>予約完了</title>
</head>

<body>

<h1>予約完了</h1>

<p>ご予約を受け付けました。</p>

<hr>

<p>
    お名前：
    ${order.customerName}
</p>

<p>
    商品ID：
    ${order.productId}
</p>

<p>
    受取希望日：
    ${order.reservationDate}
</p>

<p>
    受取希望時間：
    ${order.reservationTime}
</p>

<p>
    メールアドレス：
    ${order.email}
</p>

<p>
    電話番号：
    ${order.phone}
</p>

<hr>

<a href="${pageContext.request.contextPath}/menu">
    メニューへ戻る
</a>

</body>
</html>