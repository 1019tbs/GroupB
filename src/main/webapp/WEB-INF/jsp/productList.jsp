<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head><link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/favicon.ico">
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
    <form action="${pageContext.request.contextPath}/cart/add"
          method="post">

        <input type="hidden"
               name="productId"
               value="${product.productId}">

        <input type="hidden"
               name="quantity"
               value="1">

        <button type="submit">カートに入れる</button>
    </form>
</td>
    </c:forEach>

</table>

</body>
</html>