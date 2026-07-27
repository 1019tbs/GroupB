<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%-- JSTLの基本タグを使用するための設定 --%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%-- 金額を「1,000」のように表示するための設定 --%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>在庫管理</title>

<style>
body {
    font-family: sans-serif;
    margin: 30px;
    background-color: #f7f7f7;
}

main {
    max-width: 1000px;
    margin: 0 auto;
    padding: 24px;
    background-color: white;
}

table {
    width: 100%;
    border-collapse: collapse;
}

th,
td {
    border: 1px solid #cccccc;
    padding: 10px;
    text-align: left;
}

th {
    background-color: #eeeeee;
}

/* 在庫が0の場合 */
.stock-zero {
    color: #c62828;
    font-weight: bold;
}

/* 在庫が1～5個の場合 */
.stock-low {
    color: #ef6c00;
    font-weight: bold;
}

/* 更新成功メッセージ */
.message-success {
    color: #1b5e20;
}

/* エラーメッセージ */
.message-error {
    color: #c62828;
}

input[type="number"] {
    width: 90px;
}

button {
    padding: 6px 12px;
}
</style>
</head>

<body>

<main>

    <h1>在庫管理</h1>

    <%--
        在庫更新に成功した場合のメッセージです。
    --%>
    <c:if test="${not empty successMessage}">
        <p class="message-success">
            <c:out value="${successMessage}" />
        </p>
    </c:if>

    <%--
        入力エラーやDBエラーが発生した場合の
        メッセージです。
    --%>
    <c:if test="${not empty errorMessage}">
        <p class="message-error">
            <c:out value="${errorMessage}" />
        </p>
    </c:if>

    <table>

        <thead>
            <tr>
                <th>商品ID</th>
                <th>商品名</th>
                <th>価格</th>
                <th>現在庫</th>
                <th>在庫変更</th>
            </tr>
        </thead>

        <tbody>

            <%--
                Controllerから渡されたproductListを
                1件ずつ繰り返し表示します。
            --%>
            <c:forEach
                var="product"
                items="${productList}">

                <tr>

                    <%-- 商品ID --%>
                    <td>
                        <c:out
                            value="${product.productId}" />
                    </td>

                    <%-- 商品名 --%>
                    <td>
                        <c:out
                            value="${product.productName}" />
                    </td>

                    <%-- 商品価格 --%>
                    <td>
                        <fmt:formatNumber
                            value="${product.price}"
                            pattern="#,##0" />円
                    </td>

                    <%-- 在庫数 --%>
                    <td>

                        <c:choose>

                            <%-- 在庫が0の場合 --%>
                            <c:when test="${product.stock == 0}">
                                <span class="stock-zero">
                                    在庫切れ
                                </span>
                            </c:when>

                            <%-- 在庫が1個から5個の場合 --%>
                            <c:when test="${product.stock <= 5}">
                                <span class="stock-low">
                                    <c:out
                                        value="${product.stock}" />
                                    個（残り僅か）
                                </span>
                            </c:when>

                            <%-- 在庫が6個以上の場合 --%>
                            <c:otherwise>
                                <c:out
                                    value="${product.stock}" />個
                            </c:otherwise>

                        </c:choose>

                    </td>

                    <%-- 在庫更新フォーム --%>
                    <td>

                        <form
                            method="post"
                            action="${pageContext.request.contextPath}/inventory/update">

                            <%--
                                更新する商品のproductIdを
                                非表示でControllerへ送信します。
                            --%>
                            <input
                                type="hidden"
                                name="productId"
                                value="${product.productId}">

                            <%--
                                更新後の在庫数です。

                                min="0"
                                マイナス値の入力を防ぎます。

                                required
                                未入力での送信を防ぎます。
                            --%>
                            <input
                                type="number"
                                name="stock"
                                min="0"
                                required
                                value="${product.stock}">

                            <button type="submit">
                                更新
                            </button>

                        </form>

                    </td>

                </tr>

            </c:forEach>

            <%-- 商品が1件も登録されていない場合 --%>
            <c:if test="${empty productList}">
                <tr>
                    <td colspan="5">
                        商品が登録されていません。
                    </td>
                </tr>
            </c:if>

        </tbody>

    </table>

</main>

</body>
</html>