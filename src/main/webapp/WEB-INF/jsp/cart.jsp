<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ショッピングカート</title>
</head>

<body>

<h1>ショッピングカート</h1>

<c:set var="total" value="0" />

<table border="1">
    <tr>
        <th>商品名</th>
        <th>価格</th>
        <th>数量</th>
        <th>小計</th>
    </tr>

    <c:forEach var="item" items="${cartList}">

        <c:set var="total"
          value="${total + item.subtotal}" />

        <tr>
            <td>${item.product.productName}</td>
            <td>${item.product.price}円</td>
            <td>${item.quantity}</td>
            <td>${item.subtotal}円</td>
        </tr>

    </c:forEach>

</table>

<h2>
    合計：
    <fmt:formatNumber value="${total}" pattern="#,##0" />円
</h2>

<br>

<a href="${pageContext.request.contextPath}/menu">
    商品一覧に戻る
</a>

</body>
</html>