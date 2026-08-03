<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>注文者情報入力</title>
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/css/style.css">
</head>

<body class="checkoutPage">

<main class="checkoutContainer">

    <h1>
        <c:choose>
            <c:when test="${checkoutForm.fulfillmentMethod == 'PICKUP'}">
                店頭受取予約情報入力
            </c:when>
            <c:otherwise>注文者情報入力</c:otherwise>
        </c:choose>
    </h1>

    <p>
        会員登録情報を初期表示しています。
        この画面で変更しても会員情報そのものは更新されません。
    </p>

    <c:if test="${not empty errorMessage}">
        <p class="errorMsg">
            <c:out value="${errorMessage}" />
        </p>
    </c:if>

    <section class="checkoutCartSummary">

        <h2>購入予定の商品</h2>

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

    <form
        action="${pageContext.request.contextPath}/checkout/confirm"
        method="post"
        class="checkoutForm">

        <input type="hidden"
            name="fulfillmentMethod"
            value="${checkoutForm.fulfillmentMethod}">

        <div class="formGroup">
            <label for="customerName">
                氏名
            </label>

            <input
                type="text"
                id="customerName"
                name="customerName"
                value="${fn:escapeXml(checkoutForm.customerName)}"
                maxlength="100"
                required>
        </div>

        <c:if test="${checkoutForm.fulfillmentMethod == 'DELIVERY'}">
            <div class="formGroup">
                <label for="postalCode">郵便番号</label>
                <input type="text"
                    id="postalCode"
                    name="postalCode"
                    value="${fn:escapeXml(checkoutForm.postalCode)}"
                    maxlength="20"
                    placeholder="100-0001"
                    required>
            </div>

            <div class="formGroup">
                <label for="address">住所</label>
                <textarea id="address"
                    name="address"
                    maxlength="255"
                    rows="3"
                    required><c:out value="${checkoutForm.address}" /></textarea>
            </div>
        </c:if>

        <c:if test="${checkoutForm.fulfillmentMethod == 'PICKUP'}">
            <div class="formGroup">
                <label for="pickupDate">受取希望日</label>
                <input type="date"
                    id="pickupDate"
                    name="pickupDate"
                    value="${fn:escapeXml(checkoutForm.pickupDate)}"
                    required>
            </div>

            <div class="formGroup">
                <label for="pickupTime">受取希望時間</label>
                <input type="time"
                    id="pickupTime"
                    name="pickupTime"
                    value="${fn:escapeXml(checkoutForm.pickupTime)}"
                    required>
            </div>
        </c:if>

        <div class="formGroup">
            <label for="phone">
                電話番号
            </label>

            <input
                type="text"
                id="phone"
                name="phone"
                value="${fn:escapeXml(checkoutForm.phone)}"
                maxlength="20"
                placeholder="090-1234-5678"
                required>
        </div>

        <div class="formGroup">
            <label for="email">
                メールアドレス
            </label>

            <input
                type="email"
                id="email"
                name="email"
                value="${fn:escapeXml(checkoutForm.email)}"
                maxlength="255"
                placeholder="example@example.com"
                required>
        </div>

        <div class="formGroup">
            <label for="paymentMethod">
                支払方法
            </label>

            <select
                id="paymentMethod"
                name="paymentMethod"
                required>

                <option value="">
                    選択してください
                </option>

                <option value="credit"
                    <c:if test="${checkoutForm.paymentMethod == 'credit'}">
                        selected
                    </c:if>>
                    クレジットカード
                </option>

                <option value="bank"
                    <c:if test="${checkoutForm.paymentMethod == 'bank'}">
                        selected
                    </c:if>>
                    銀行振込
                </option>

                <c:if test="${checkoutForm.fulfillmentMethod == 'DELIVERY'}">
                    <option value="cash_on_delivery"
                        <c:if test="${checkoutForm.paymentMethod == 'cash_on_delivery'}">
                            selected
                        </c:if>>
                        代金引換
                    </option>
                </c:if>

                <option value="convenience_store"
                    <c:if test="${checkoutForm.paymentMethod == 'convenience_store'}">
                        selected
                    </c:if>>
                    コンビニ払い
                </option>

                <c:if test="${checkoutForm.fulfillmentMethod == 'PICKUP'}">
                    <option value="pay_at_store"
                        <c:if test="${checkoutForm.paymentMethod == 'pay_at_store'}">
                            selected
                        </c:if>>
                        店頭支払い
                    </option>
                </c:if>
            </select>
        </div>

        <div class="checkoutActions">
            <a href="${pageContext.request.contextPath}/cart">
                カートに戻る
            </a>

            <a href="${pageContext.request.contextPath}/menu">
                メニューに戻る
            </a>

            <button type="submit">
                次へ
            </button>
        </div>

    </form>

</main>

</body>
</html>