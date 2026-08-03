<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ショッピングカート</title>
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/css/style.css">
</head>

<body class="cartPage">

<main class="cartContainer">

    <h1>ショッピングカート</h1>

    <c:if test="${not empty fulfillmentMethod}">
        <p class="cartFulfillmentMethod">
            受取方法：
            <strong>
                <c:choose>
                    <c:when test="${fulfillmentMethod == 'PICKUP'}">
                        店頭受取
                    </c:when>
                    <c:otherwise>通販</c:otherwise>
                </c:choose>
            </strong>
        </p>
    </c:if>

    <c:if test="${not empty cartMessage}">
        <p class="successMessage">
            <c:out value="${cartMessage}" />
        </p>
    </c:if>

    <c:if test="${not empty cartErrorMessage}">
        <p class="errorMsg">
            <c:out value="${cartErrorMessage}" />
        </p>
    </c:if>

    <c:choose>
        <c:when test="${empty cartList}">

            <p>カートに商品がありません。</p>

            <a href="${pageContext.request.contextPath}/menu">
                商品一覧に戻る
            </a>

        </c:when>

        <c:otherwise>

            <c:set var="total" value="0" />
            <c:set var="canPurchase" value="true" />

            <table class="cartTable" border="1">
                <thead>
                    <tr>
                        <th>商品</th>
                        <th>価格</th>
                        <th>在庫</th>
                        <th>数量</th>
                        <th>小計</th>
                        <th>状態</th>
                        <th>取消</th>
                    </tr>
                </thead>

                <tbody>
                    <c:forEach var="item"
                        items="${cartList}">

                        <c:set var="total"
                            value="${total + item.subtotal}" />

                        <c:if test="${not item.product.active
                                or item.product.stock <= 0
                                or item.quantity > item.product.stock
                                or (fulfillmentMethod == 'DELIVERY'
                                    and not item.product.deliveryAvailable)
                                or (fulfillmentMethod == 'PICKUP'
                                    and not item.product.pickupAvailable)}">

                            <c:set var="canPurchase"
                                value="false" />
                        </c:if>

                        <tr>
                            <td>
                                <c:if test="${not empty item.product.imageUrl}">
                                    <img
                                        src="${pageContext.request.contextPath}${item.product.imageUrl}"
                                        alt="<c:out value='${item.product.productName}' />"
                                        width="90">
                                </c:if>

                                <div>
                                    <c:out value="${item.product.productName}" />
                                </div>
                            </td>

                            <td>
                                <fmt:formatNumber
                                    value="${item.product.price}"
                                    pattern="#,##0" />円
                            </td>

                            <td>
                                ${item.product.stock}
                            </td>

                            <td>
                                <form
                                    action="${pageContext.request.contextPath}/cart/update"
                                    method="post">

                                    <input type="hidden"
                                        name="productId"
                                        value="${item.product.productId}">

                                    <input type="number"
                                        name="quantity"
                                        min="1"
                                        max="${item.product.stock}"
                                        value="${item.quantity}"
                                        <c:if test="${not item.product.active
                                                or item.product.stock <= 0}">
                                            disabled
                                        </c:if>>

                                    <button type="submit"
                                        <c:if test="${not item.product.active
                                                or item.product.stock <= 0}">
                                            disabled
                                        </c:if>>
                                        変更
                                    </button>
                                </form>
                            </td>

                            <td>
                                <fmt:formatNumber
                                    value="${item.subtotal}"
                                    pattern="#,##0" />円
                            </td>

                            <td>
                                <c:choose>
                                    <c:when test="${not item.product.active}">
                                        取扱停止
                                    </c:when>

                                    <c:when test="${item.product.stock <= 0}">
                                        在庫切れ
                                    </c:when>

                                    <c:when test="${item.quantity > item.product.stock}">
                                        在庫不足
                                        （残り${item.product.stock}個）
                                    </c:when>

                                    <c:when test="${fulfillmentMethod == 'DELIVERY'
                                            and not item.product.deliveryAvailable}">
                                        通販対象外
                                    </c:when>

                                    <c:when test="${fulfillmentMethod == 'PICKUP'
                                            and not item.product.pickupAvailable}">
                                        店頭受取対象外
                                    </c:when>

                                    <c:otherwise>
                                        購入可能
                                    </c:otherwise>
                                </c:choose>
                            </td>

                            <td>
                                <form
                                    action="${pageContext.request.contextPath}/cart/remove"
                                    method="post">

                                    <input type="hidden"
                                        name="productId"
                                        value="${item.product.productId}">

                                    <button type="submit">
                                        取消
                                    </button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <h2>
                合計：
                <fmt:formatNumber
                    value="${total}"
                    pattern="#,##0" />円
            </h2>

            <c:if test="${not canPurchase}">
                <p class="errorMsg">
                    取扱停止・在庫切れ・在庫不足の商品があります。
                    数量を修正するか、商品を取り消してください。
                </p>
            </c:if>

            <div class="cartActions">
                <a href="${pageContext.request.contextPath}/menu">
                    商品一覧に戻る
                </a>

                <c:choose>
                    <c:when test="${canPurchase}">
                        <a href="${pageContext.request.contextPath}/checkout/input">
                            <c:choose>
                                <c:when test="${fulfillmentMethod == 'PICKUP'}">
                                    店頭受取を予約する
                                </c:when>
                                <c:otherwise>購入する</c:otherwise>
                            </c:choose>
                        </a>
                    </c:when>

                    <c:otherwise>
                        <button type="button" disabled>
                            注文手続きへ
                        </button>
                    </c:otherwise>
                </c:choose>
            </div>

        </c:otherwise>
    </c:choose>

</main>

</body>
</html>
