<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>管理者画面</title>
</head>

<body>
	<h1 class= "TopIcon">Honey Bloom
	<img alt="アイコン" src="${pageContext.request.contextPath}/images/icon.png" class= "icon" >
	</h1>
	<p>－ 管理者ログイン中 －</p>
	
	<h2>管理者画面</h2>
	<div>
	
	</div>
	<form action="${pageContext.request.contextPath}/admin/contact"
		method="get">
		<button type="submit">
		<img alt="メール" src="${pageContext.request.contextPath}/images/icon_mail.png">
		お問い合わせ一覧
		</button>
	</form>

	<br>

	<form action="${pageContext.request.contextPath}/admin/order"
		method="get">
		<button type="submit">
		<img alt="予約" src="${pageContext.request.contextPath}/images/icon_yoyaku.png">
		予約一覧
		</button>
	</form>

	<br>

	<form action="${pageContext.request.contextPath}/inventory"
		method="get">
		<button type="submit">
		<img alt="在庫" src="${pageContext.request.contextPath}/images/icon_zaiko.png">
		在庫確認
		</button>
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