<!-- ログイン画面 -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Honey Bloom</title>
<!-- cssファイル読み込み -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>
<h1 style="color:#783f04" >Honey Bloom</h1>
<form action="Login" method= "post">
    <label>会員ID</label>
    <input type="text" name= "name"
    placeholder= "会員IDを入力"><br>
    <label>パスワード</label>
    <input type="password" name= "pass"
    placeholder= "パスワードを入力"><br>
    <input type= "submit" value= "ログイン">
</form>
<div>または</div>
<button>新規会員登録はこちら</button>

</body>

</html>