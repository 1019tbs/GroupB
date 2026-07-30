<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ご予約ありがとうございました></title>
<!-- cssファイル読み込み -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css">

</head>

<body class="thankyouPage">
<img class= "thankyouBgImg" alt="予約完了背景" src="${pageContext.request.contextPath}/images/thankyou_bg.png">
	<div class= "thankyouContainer">
		<div class= "shopTitle">
			<h1>Honey Bloom
			<img class= "icon" alt="アイコン" src="${pageContext.request.contextPath}/images/icon.png">
			</h1>
			<p>はちみつ香る、しあわせなお菓子時間。</p>
		</div>
		<div class= "thankContent">
			<h2>Thank you<br>
			for your order!</h2>
			<img class= "line2" alt="line2" src="${pageContext.request.contextPath}/images/line2.png">
			<p class= "thankText">
			ご予約ありがとうございました。<br>
			ご注文内容は、ご登録のメールアドレスへお送りしています。
			</p>
			<img class= "thankImg" alt="梱包画像" src="${pageContext.request.contextPath}/images/thankyou.png">
			<div class = "thankWaitMessage">
			Honey Bloomのお菓子が届くまで、<br>
			もうしばらくお待ちください。
			</div>
			<a class= "backButton" href="${pageContext.request.contextPath}/main">
			TOPへ戻る
			</a>
		</div>
	</div>
	<jsp:include page="common/footer.jsp"/>
</body>
</html>
