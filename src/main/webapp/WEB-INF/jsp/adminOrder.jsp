<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>予約一覧</title>
</head>

<body>

    <h1>予約一覧</h1>

    <c:choose>

        <!-- データが存在しない場合 -->
        <c:when test="${empty orderList}">
            <p>予約情報はありません。</p>
        </c:when>

        <!-- データが存在する場合 -->
        <c:otherwise>

            <table border="1">

                <tr>
                    <th>予約ID</th>
                    <th>お客様名</th>
                    <th>メールアドレス</th>
                    <th>電話番号</th>
                    <th>商品名</th>
                    <th>予約日</th>
                    <th>予約時間</th>
                    <th>登録日時</th>
                </tr>

                <c:forEach var="order" items="${orderList}">

                    <tr>

                        <td>${order.orderId}</td>

                        <td>${order.customerName}</td>

                        <td>${order.email}</td>

                        <td>${order.phone}</td>

                        <td>${order.productName}</td>

                        <td>${order.reservationDate}</td>

                        <td>${order.reservationTime}</td>

                        <td>${order.createdAt}</td>

                    </tr>

                </c:forEach>

            </table>

        </c:otherwise>

    </c:choose>

    <br>

    <form action="${pageContext.request.contextPath}/admin" method="get">
        <button type="submit">管理者画面へ戻る</button>
    </form>

</body>
</html>