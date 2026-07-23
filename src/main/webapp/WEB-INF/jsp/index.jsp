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

<body class= "loginPage">
<h1 >Honey Bloom
<img alt="アイコン" src="${pageContext.request.contextPath}/images/icon.png" class= "icon" >
</h1>
<p>はちみつ香る、しあわせなお菓子時間</p>
<form action="Login" method= "post">
	<label>会員ID</label>
	<input type="text" name= "name"
	placeholder= "会員IDを入力"><br>
	<label>パスワード</label>
	<input type="password" name= "pass"
	placeholder= "パスワードを入力"><br>
	<input type= "submit" value= "ログイン">
</form>
<div class= "or">または</div>
<form action="${pageContext.request.contextPath}/registration" method= "get">
	<button type= "submit">新規会員登録はこちら</button>
</form>
<jsp:include page="common/footer.jsp" />
</body>

</html>