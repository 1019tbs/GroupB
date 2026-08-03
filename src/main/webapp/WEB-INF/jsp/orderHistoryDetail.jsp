<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>注文履歴詳細</title>
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/css/style.css">
</head>

<body class="orderHistoryPage">

<main class="orderHistoryContainer">

    <h1>注文履歴詳細</h1>

    <section class="orderHistoryCard">

        <h2>
            <c:out value="${order.fulfillmentMethodLabel}" />
            ／ 注文番号：
            <c:out
                value="${order.shoppingOrderId}" />
        </h2>

        <p>
            受付日時：
            <c:out
                value="${order.createdAtText}" />
        </p>

        <c:if test="${order.pickup}">
            <p>
                受取日時：
                <c:out value="${order.pickupDateText}" />
                <c:out value="${order.pickupTimeText}" />
            </p>
        </c:if>

        <p>
            状態：
            <c:out
                value="${order.orderStatusLabel}" />
        </p>

        <h3>購入商品</h3>

        <table class="cartTable" border="1">
            <thead>
                <tr>
                    <th>商品名</th>
                    <th>単価</th>
                    <th>数量</th>
                    <th>小計</th>
                </tr>
            </thead>

            <tbody>
                <c:forEach var="item"
                    items="${order.items}">

                    <tr>
                        <td>
                            <c:out
                                value="${item.productName}" />
                        </td>

                        <td>
                            <fmt:formatNumber
                                value="${item.unitPrice}"
                                pattern="#,##0" />円
                        </td>

                        <td>
                            ${item.quantity}
                        </td>

                        <td>
                            <fmt:formatNumber
                                value="${item.subtotal}"
                                pattern="#,##0" />円
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <p>
            合計：
            <strong>
                <fmt:formatNumber
                    value="${order.totalAmount}"
                    pattern="#,##0" />円
            </strong>
        </p>

        <h3>注文者情報</h3>

        <dl>
            <dt>氏名</dt>
            <dd>
                <c:out
                    value="${order.customerName}" />
            </dd>

            <c:if test="${order.delivery}">
                <dt>郵便番号</dt>
                <dd>
                    <c:out value="${order.postalCode}" />
                </dd>

                <dt>住所</dt>
                <dd>
                    <c:out value="${order.address}" />
                </dd>
            </c:if>

            <dt>電話番号</dt>
            <dd>
                <c:out
                    value="${order.phone}" />
            </dd>

            <dt>メールアドレス</dt>
            <dd>
                <c:out
                    value="${order.email}" />
            </dd>

            <dt>支払方法</dt>
            <dd>
                <c:out
                    value="${order.paymentMethodLabel}" />
            </dd>
        </dl>

    </section>

    <div class="orderHistoryActions">
        <a href="${pageContext.request.contextPath}/orders/history">
            注文履歴一覧へ戻る
        </a>

        <a href="${pageContext.request.contextPath}/menu">
            メニューへ戻る
        </a>
    </div>

</main>

</body>
</html>
