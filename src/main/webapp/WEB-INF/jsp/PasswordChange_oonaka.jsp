<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>パスワード変更</title>
</head>
<body>

<h1>パスワード変更</h1>

<c:if test="${not empty errorMsg}">
    <p style="color:red;">
        <c:out value="${errorMsg}" />
    </p>
</c:if>

<form action="${pageContext.request.contextPath}/member/password_oonaka"
      method="post">

    <p>
        現在のパスワード：
        <input type="password"
               name="currentPassword">
    </p>

    <p>
        新しいパスワード：
        <input type="password"
               name="newPassword">
    </p>

    <p>
        新しいパスワード（確認）：
        <input type="password"
               name="confirmPassword">
    </p>

    <button type="submit">
        パスワードを変更する
    </button>

</form>

<p>
    <a href="${pageContext.request.contextPath}/member/edit_oonaka">
        会員情報変更画面へ戻る
    </a>
</p>

</body>
</html>