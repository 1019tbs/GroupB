<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Menu</title>
<!-- cssファイル読み込み -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css">
</head>

<body class="menu">
<img class="menuTopImg" alt="メニュー上部画像" src="${pageContext.request.contextPath}/images/menu_top.png">
<div class= "menuHeader">
	<nav class= "menuNav">
		<a href="#cakes">CAKES</a>
		/
		<a href="#bakes">BAKES</a>
		/
		<a href="#pastries">PASTRIES</a>
	</nav>
</div>

	<section id= "cake">
	
	</section>
	<section id= "bakes">
	
	</section>
	<section id= "pastries">
	
	</section>




	<form action="${pageContext.request.contextPath}/form/submit"
		method="post">

		<!-- 予約を表す隠し項目 -->
		<input type="hidden" name="genre" value="reservation"> <label>お名前</label>
		<input type="text" name="customerName"> <label>メールアドレス</label>
		<input type="email" name="email"> <label>電話番号</label> <input
			type="text" name="phone"> <label>メニュー</label> <select
			name="menuId">
			<option value="">選択してください</option>

			<option value="1">ハニートースト</option>

			<option value="2">フラワーティー</option>
		</select> <label>予約日</label> <input type="date" name="reservationDate">

		<label>予約時間</label> <input type="time" name="reservationTime">

		<label>予約人数</label> <input type="number" name="numberOfPeople" min="1">

		<p style="color: red;">${errorMsg}</p>

		<button type="submit">予約する</button>

	</form>
	
	<jsp:include page="common/footer.jsp"/>
	</body>
</html>