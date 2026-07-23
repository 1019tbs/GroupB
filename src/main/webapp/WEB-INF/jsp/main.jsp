<!-- 最初の画面 -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>HOME</title>
<!-- cssファイル読み込み -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>

<body class= "mainPage">

<img alt="メイン画像" src="${pageContext.request.contextPath}/images/main.png" class= "mainImg">

<!-- 上部 -->
<div class= "topArea">
	<h1>Honey Bloom
	<img alt="アイコン" src="${pageContext.request.contextPath}/images/icon.png" class= "icon" >
	</h1>
	<p>はちみつ香る、しあわせなお菓子時間。</p>
	<h2>${loginUser.userName}さん、ようこそ</h2>
</div>

<!-- 商品ラインナップ、会員情報変更、お問い合わせ -->
<div class= "contentsArea">
	<div class= "leftArea">
		<h2 class= "menuTitle">MENU</h2>
		
		<div class= "menuGroup">
			<div class= "menuImg">
				<img alt="ケーキ" src="${pageContext.request.contextPath}/images/cake.png">
				<img alt="クッキー" src="${pageContext.request.contextPath}/images/cookies.png">
				<img alt="その他" src="${pageContext.request.contextPath}/images/otherSweets.png">
			</div>
				<form action="menu" method= "get">
				<button type= "submit" class="menuButton">商品ラインナップ</button>
				</form>		
		</div>
	

		<div class="subButtonArea">

			<form action="${pageContext.request.contextPath}/member/edit_oonaka" method="get">
                <button type="submit" class="subButton">会員情報変更</button>
            </form>
			
		    <form action="form" method= "get">
		    	<button type= "submit" class="subButton">お問い合わせ</button>
		    </form>
		</div>
	</div>	
</div>
	
<div class="footer">
	
	<div class= "adminArea">
		<form action= "admin" method= "get">
		<button class= "adminButton">管理者画面</button>
    	</form>
	</div>
    <small class= "copyright">©Honey Bloom / since1880</small>
</div>


</body>
</html>