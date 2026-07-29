<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>注文履歴</title>
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/css/style.css">
</head>

<body class="orderHistoryPage">

<main class="orderHistoryContainer">

    <h1>注文履歴</h1>

    <c:if test="${not empty historyErrorMessage}">
        <p class="errorMsg">
            <c:out value="${historyErrorMessage}" />
        </p>
    </c:if>

    <c:choose>
        <c:when test="${empty orderList}">

            <p>注文履歴はありません。</p>

        </c:when>

        <c:otherwise>

            <c:forEach var="order"
                items="${orderList}">

                <section class="orderHistoryCard">

                    <h2>
                        注文番号：
                        <c:out
                            value="${order.shoppingOrderId}" />
                    </h2>

                    <p>
                        購入日時：
                        <c:out
                            value="${order.createdAtText}" />
                    </p>

                    <p>
                        状態：
                        <c:out
                            value="${order.orderStatusLabel}" />
                    </p>

                    <ul>
                        <c:forEach var="item"
                            items="${order.items}">

                            <li>
                                <c:out
                                    value="${item.productName}" />
                                × ${item.quantity}個
                            </li>
                        </c:forEach>
                    </ul>

                    <p>
                        合計：
                        <strong>
                            <fmt:formatNumber
                                value="${order.totalAmount}"
                                pattern="#,##0" />円
                        </strong>
                    </p>

                    <a href="${pageContext.request.contextPath}/orders/history/detail?orderId=${order.shoppingOrderId}">
                        詳細を見る
                    </a>

                </section>
            </c:forEach>

        </c:otherwise>
    </c:choose>

    <div class="orderHistoryActions">
        <a href="${pageContext.request.contextPath}/menu">
            メニューへ戻る
        </a>

        <a href="${pageContext.request.contextPath}/cart">
            カートを見る
        </a>
    </div>

</main>

</body>
</html>