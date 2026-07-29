<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>購入内容確認</title>
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/css/style.css">
</head>

<body class="checkoutPage">

<main class="checkoutContainer">

    <h1>購入内容確認</h1>

    <section class="checkoutCartSummary">

        <h2>商品内容</h2>

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
                    items="${cartList}">

                    <tr>
                        <td>
                            <c:out
                                value="${item.product.productName}" />
                        </td>

                        <td>
                            <fmt:formatNumber
                                value="${item.product.price}"
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

        <p class="checkoutTotal">
            合計：
            <strong>
                <fmt:formatNumber
                    value="${total}"
                    pattern="#,##0" />円
            </strong>
        </p>

    </section>

    <section class="checkoutCustomerSummary">

        <h2>注文者情報</h2>

        <dl>
            <dt>氏名</dt>
            <dd>
                <c:out
                    value="${checkoutForm.customerName}" />
            </dd>

            <dt>郵便番号</dt>
            <dd>
                <c:out
                    value="${checkoutForm.postalCode}" />
            </dd>

            <dt>住所</dt>
            <dd>
                <c:out
                    value="${checkoutForm.address}" />
            </dd>

            <dt>電話番号</dt>
            <dd>
                <c:out
                    value="${checkoutForm.phone}" />
            </dd>

            <dt>メールアドレス</dt>
            <dd>
                <c:out
                    value="${checkoutForm.email}" />
            </dd>

            <dt>支払方法</dt>
            <dd>
                <c:out
                    value="${paymentMethodLabel}" />
            </dd>
        </dl>

    </section>

    <p>
        「注文を確定する」を押すと、
        注文登録・在庫減算・カートクリアを実行します。
    </p>

    <div class="checkoutActions">

        <a href="${pageContext.request.contextPath}/checkout/input">
            入力内容を修正する
        </a>

        <a href="${pageContext.request.contextPath}/cart">
            カートに戻る
        </a>

        <form
            action="${pageContext.request.contextPath}/checkout/complete"
            method="post"
            onsubmit="this.querySelector('button').disabled = true;">

            <input type="hidden"
                name="checkoutToken"
                value="${checkoutToken}">

            <button type="submit">
                注文を確定する
            </button>
        </form>
    </div>

</main>

</body>
</html>