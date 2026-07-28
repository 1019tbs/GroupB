<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>管理者画面</title>
</head>

<body>

	<h1>管理者画面</h1>

	<form action="${pageContext.request.contextPath}/admin/contact"
		method="get">
		<button type="submit">お問い合わせ一覧</button>
	</form>

	<br>

	<form action="${pageContext.request.contextPath}/admin/order"
		method="get">
		<button type="submit">予約一覧</button>
	</form>

	<br>

	<form action="${pageContext.request.contextPath}/inventory"
		method="get">
		<button type="submit">在庫確認</button>
	</form>
	
	<br>
	
	<form action="${pageContext.request.contextPath}/admin/member"
		method="get">
		<button type="submit">会員管理</button>
	</form>

	<br>
	<br>

	<form action="${pageContext.request.contextPath}/main" method="get">
		<button type="submit">メインメニューへ戻る</button>
	</form>

</body>
</html>