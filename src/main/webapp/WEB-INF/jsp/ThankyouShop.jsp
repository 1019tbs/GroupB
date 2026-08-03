<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ご注文ありがとうございました</title>
<!-- cssファイル読み込み -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css">
</head>

<body class="thankyouPage">
<img class= "thankyouBgImg" alt="予約完了背景" src="${pageContext.request.contextPath}/images/thankyou_bg.png">
	<!-- 右上メニュー -->
	<div class= "shopHeaderMenu">
	<form action="${pageContext.request.contextPath}/menu" method="get">
	<button class="headerButton" type="submit">
	<img class="headerIcon" alt="メニュー"
	     src="${pageContext.request.contextPath}/images/icon_menu.png">
	メニュー
	</button>
	</form>
		<span>/</span>

	<form action="${pageContext.request.contextPath}/cart" method="get">
	<button class="headerButton" type="submit">
	<img class="headerIcon" alt="カート"
	     src="${pageContext.request.contextPath}/images/icon_cart.png">
	カート
	</button>
	</form>

	<span>/</span>

		<form action="${pageContext.request.contextPath}/orders/history" method="get">
	<button class="headerButton" type="submit">
	<img class="headerIcon" alt="注文履歴"
	     src="${pageContext.request.contextPath}/images/icon_log.png">
	注文履歴
	</button>
	</form>
	
	</div>
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
				<p class="thankText">
				<c:choose>
					<c:when test="${fulfillmentMethod == 'PICKUP'}">
						店頭受取のご予約ありがとうございました。<br>
						受取日時：<c:out value="${pickupDate}" />
						<c:out value="${pickupTime}" />
					</c:when>
					<c:otherwise>
						ご注文ありがとうございました。
					</c:otherwise>
				</c:choose>
				<br>注文番号：<c:out value="${completedOrderId}" />
				<br>
				<c:choose>
					<c:when test="${mailSent}">
						ご注文内容をメールアドレスへ送信しました。
					</c:when>
					<c:otherwise>
						ご注文内容は注文履歴から確認できます。
					</c:otherwise>
				</c:choose>
				</p>
			<img class= "thankImg" alt="梱包画像" src="${pageContext.request.contextPath}/images/thankyou.png">
				<div class="thankWaitMessage">
				<c:choose>
					<c:when test="${fulfillmentMethod == 'PICKUP'}">
						ご指定の日時に店舗へお越しください。
					</c:when>
					<c:otherwise>
						Honey Bloomのお菓子が届くまで、<br>
						もうしばらくお待ちください。
					</c:otherwise>
				</c:choose>
				</div>
			<a class= "backButton" href="${pageContext.request.contextPath}/main">
			TOPへ戻る
			</a>
		</div>
	</div>
	<jsp:include page="common/footer.jsp"/>
</body>
</html>
