<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>注文完了</title>
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/css/style.css">
</head>

<body class="checkoutPage">

<main class="checkoutContainer">

    <h1>注文完了</h1>

    <p>
        ご注文ありがとうございました。
    </p>

    <p>
        注文番号：
        <strong>
            <c:out value="${completedOrderId}" />
        </strong>
    </p>

    <p>
        注文内容を正常に登録し、
        商品在庫とショッピングカートを更新しました。
    </p>

    <c:choose>
        <c:when test="${mailSent}">
            <p>
                注文完了メールを送信しました。
            </p>
        </c:when>

        <c:otherwise>
            <p>
                注文は正常に完了しています。
                メール機能が無効、またはメール送信に失敗したため、
                メールは送信されていません。
            </p>
        </c:otherwise>
    </c:choose>

    <div class="checkoutActions">
        <a href="${pageContext.request.contextPath}/orders/history">
            注文履歴を見る
        </a>

        <a href="${pageContext.request.contextPath}/menu">
            メニューへ戻る
        </a>

        <a href="${pageContext.request.contextPath}/cart">
            カートを確認する
        </a>
    </div>

</main>

</body>
</html>