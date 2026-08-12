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

<link rel="stylesheet"
    href="${pageContext.request.contextPath}/css/orderHistory.css">

</head>

<body class="orderHistoryPage">

<jsp:include page="/WEB-INF/jsp/common/header.jsp" />

<main class="orderHistoryContainer">

    <section class="orderHistoryHero">

        <img class="orderHistoryHeroImg"
            src="${pageContext.request.contextPath}/images/orderHistory_title.png"
            alt="">

        <div class="orderHistoryHeroOverlay">

            <h1>ご注文履歴</h1>

            <span class="heroLine"></span>

        </div>

    </section>

    <c:if test="${not empty historyErrorMessage}">
        <p class="errorMsg">
            <c:out value="${historyErrorMessage}" />
        </p>
    </c:if>

    <c:choose>

        <c:when test="${empty orderList}">

            <p class="noOrderMessage">
                注文履歴はありません。
            </p>

        </c:when>

        <c:otherwise>

            <c:forEach var="order"
                items="${orderList}">

                <section class="orderHistoryCard">

                    <div class="orderHistoryCardTop">

                        <div class="orderHistoryCardInfo">

                            <h2>
                                注文番号：
                                <c:out value="${order.shoppingOrderId}" />
                            </h2>

                            <p class="orderMethod">
                                <c:out value="${order.fulfillmentMethodLabel}" />
                            </p>

                        </div>

                        <span class="orderStatus">
                            <c:out value="${order.orderStatusLabel}" />
                        </span>

                    </div>

                    <p class="orderDate">
                        受付日時：
                        <c:out value="${order.createdAtText}" />
                    </p>

                    <c:if test="${order.pickup}">
                        <p class="pickupDate">
                            受取日時：
                            <c:out value="${order.pickupDateText}" />
                            <c:out value="${order.pickupTimeText}" />
                        </p>
                    </c:if>

                    <div class="orderItemsArea">

                        <div class="orderItemsHeader">
                            <span>商品名</span>
                            <span>数量</span>
                        </div>

                        <ul class="orderItemList">

                            <c:forEach var="item"
                                items="${order.items}">

                                <li>

                                    <span class="orderItemName">
                                        <c:out value="${item.productName}" />
                                    </span>

                                    <span class="orderItemQuantity">
                                        × ${item.quantity}個
                                    </span>

                                </li>

                            </c:forEach>

                        </ul>

                    </div>

                    <div class="orderCardBottom">

                        <p class="orderTotal">
                            合計：
                            <strong>
                                <fmt:formatNumber
                                    value="${order.totalAmount}"
                                    pattern="#,##0" />円
                            </strong>
                        </p>

                        <a class="orderDetailButton"
                            href="${pageContext.request.contextPath}/orders/history/detail?orderId=${order.shoppingOrderId}">
                            詳細を見る
                            <span class="detailArrow">›</span>
                        </a>

                    </div>

                </section>

            </c:forEach>

        </c:otherwise>

    </c:choose>

    <div class="orderHistoryActions">

        <a href="${pageContext.request.contextPath}/main">
            TOPへ戻る
        </a>

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