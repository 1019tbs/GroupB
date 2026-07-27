<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商品一覧</title>
</head>
<body>

<h2>商品一覧</h2>

<table border="1">
    <tr>
        <th>商品ID</th>
        <th>商品名</th>
        <th>価格</th>
        <th>在庫</th>
        <th>商品説明</th>
        <th>操作</th>
    </tr>

    <c:forEach var="product" items="${productList}">
        <tr>
            <td>${product.productId}</td>
            <td>${product.productName}</td>
            <td>${product.price}円</td>
            <td>${product.stock}</td>
            <td>${product.description}</td>
            <td>
                <button type="button">カートに入れる</button>
            </td>
        </tr>
    </c:forEach>

</table>

</body>
</html>