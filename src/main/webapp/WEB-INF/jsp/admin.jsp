<!-- 管理者画面 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>管理者画面</title>
<!-- cssファイル読み込み -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">

</head>

<body class= "adminPage">
	<img class= "adminFrill" alt="上部フリル" src="${pageContext.request.contextPath}/images/frill.png" >
	<header class= "adminHeader">
		<div class= "adminLogo">
			<h1 class= "TopIcon">Honey Bloom
			<img class= "icon" alt="アイコン" src="${pageContext.request.contextPath}/images/icon.png" >
			</h1>
			<p>－ 管理者ログイン中 －</p>
		</div>
		<form action="${pageContext.request.contextPath}/main" method="get">
		<button class= "adminBackButton" type= "submit">
		メインメニューへ戻る
		</button>
		</form>
	</header>
	<div class= "adminMain">	
		<h2 class= "adminTitle">管理者画面</h2>
		<div class= "adminMenuBox">
			<form action="${pageContext.request.contextPath}/admin/contact"
				method="get">
				<button type="submit">
				<img alt="問い合わせ" src="${pageContext.request.contextPath}/images/icon_mail.png">
				<span>お問い合わせ一覧</span>
				</button>
			</form>
			<form action="${pageContext.request.contextPath}/admin/order"
				method="get">
				<button type="submit">
				<img alt="予約" src="${pageContext.request.contextPath}/images/icon_yoyaku.png">
				<span>予約一覧</span>
				</button>
			</form>			
			<form action="${pageContext.request.contextPath}/inventory"
				method="get">
				<button type="submit">
				<img alt="在庫" src="${pageContext.request.contextPath}/images/icon_zaiko.png">
				<span>在庫確認</span>
				</button>
			</form>
			<form action="${pageContext.request.contextPath}/admin/member"
				method="get">
				<button type="submit">
				<img alt="会員" src="">
				<span>会員管理</span>
				</button>
			</form>
		</div>
	</div>
	<jsp:include page="common/footer.jsp"/>
</body>
</html>