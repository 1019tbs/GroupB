<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>パスワード変更完了</title>
</head>
<body>

<h1>パスワード変更完了</h1>

<p>
    パスワードを変更しました。
</p>

<form action="${pageContext.request.contextPath}/main"
      method="get">

    <button type="submit">
        メニューへ戻る
    </button>

</form>

</body>
</html>