<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<header>
	<!-- 上部フリル -->
	<img class="headerImg" alt="ヘッダー"
		src="${pageContext.request.contextPath}/images/frill.png"> <img
		class="mainImg" alt="メイン"
		src="${pageContext.request.contextPath}/images/main.png">

	<!-- 右上メニュー -->
	<div class="headerMenu">
		<form action="${pageContext.request.contextPath}/main" method="get">
			<button class="headerButton" type="submit">
				<img class="headerIcon" alt="メインメニューに戻る"
					src="${pageContext.request.contextPath}/images/icon_main.png">
				メインメニューへ
			</button>
		</form>

		<span>/</span>

		<form action="${pageContext.request.contextPath}/Logout" method="post">
			<button class="headerButton" type="submit">
				<img class="headerIcon" alt="ログアウト"
					src="${pageContext.request.contextPath}/images/icon_logout.png">
				ログアウト
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
</header>
</html>