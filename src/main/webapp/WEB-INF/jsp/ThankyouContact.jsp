<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>お問い合わせ完了</title>
<!-- cssファイル読み込み -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css">
</head>

<body class= "contact">
	<div class= "thankContainer">
		<img class= "contactTopImg" alt="お問い合わせ完了トップ" src="${pageContext.request.contextPath}/images/thank you_mail.png">
		<h1 class= "thankTitle">THANK YOU FOR YOUR MESSAGE</h1>
		<img class="lineImg"  alt="ライン" src="${pageContext.request.contextPath}/images/line1.png">
		<h2 class="thankSubTitle">お問い合わせありがとうございました。</h2>
		<p class="thankMessage">
		お問い合わせ内容を受け付けました。<br>
		内容を確認の上、折り返しご連絡いたします。<br>
		返信まで今しばらくお待ちください。
		</p>
		
		<a class= "backButton" href="${pageContext.request.contextPath}/main">
			MENUへ戻る
		</a>
	</div>
	<jsp:include page="common/footer.jsp"/>
</body>
</html>