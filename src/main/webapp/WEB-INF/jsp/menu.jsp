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

</head>

<body class="menu">
<!--<img class="menuTopImg" alt="メニュー上部画像" src="${pageContext.request.contextPath}/images/menu_top.png">-->
<!--<div class= "menuHeader">-->
<!--	<nav class= "menuNav">-->
<!--		<a href="#cakes">CAKES</a>-->
<!--		/-->
<!--		<a href="#bakes">BAKES</a>-->
<!--		/-->
<!--		<a href="#pastries">PASTRIES</a>-->
<!--	</nav>-->
<!--</div>-->
<img class= "menuNextImg" alt="今週のベイク便り" src="${pageContext.request.contextPath}/images/menu_next.png">


	<section id= "cakes">
	<img class= "categoryCakeImg" alt="CAKES" src="${pageContext.request.contextPath}/images/category_cake.png">
	
	<div class= "menuGrid">
		<div class= "menuCard">
			<h3 class= "itemName">ラズベリー・オペラ</h3>
			<p class= "price">価格 ￥880(税込み)</p>
			<img class="itemImg" alt="ラズベリー・オペラ" src="${pageContext.request.contextPath}/images/cake_opera.png">
			<div class= "cardBottom">
			<span class= "stock">注文数</span>
			<input type= "number" name="operaCount" min= "0" value= "0">
			<button class= "cartButton">カート追加</button>
			</div>
		</div>	
		<div class= "menuCard">
			<h3 class= "itemName">ハニカム・ムースケーキ</h3>
			<p class= "price">価格 ￥690(税込み)</p>
			<img class="itemImg" alt="ハニカム・ムースケーキ" src="${pageContext.request.contextPath}/images/cake_honey.png">
			<div class= "cardBottom">
			<span class= "stock">注文数</span>
			<input type= "number" name="honeyCount" min= "0" value= "0">
			<button class= "cartButton">カート追加</button>
			</div>
		</div>
		<div class= "menuCard">
			<h3 class= "itemName">ブラックフォレスト・ドーム</h3>
			<p class= "price">価格 ￥880(税込み)</p>
			<img class="itemImg" alt="ブラックフォレスト・ドーム" src="${pageContext.request.contextPath}/images/cake_bkforest.png">
			<div class= "cardBottom">
			<span class= "stock">注文数</span>
			<input type= "number" name="bkforestCount" min= "0" value= "0">
			<button class= "cartButton">カート追加</button>
			</div>
		</div>	
		<div class= "menuCard">
			<h3 class= "itemName">バッテンバーグケーキ</h3>
			<p class= "price">価格 ￥780(税込み)</p>
			<img class="itemImg" alt="バッテンバーグケーキ" src="${pageContext.request.contextPath}/images/cake_batten.png">
			<div class= "cardBottom">
			<span class= "stock">注文数</span>
			<input type= "number" name="battenCount" min= "0" value= "0">
			<button class= "cartButton">カート追加</button>
			</div>
			
		</div>
		<div class= "menuCard">
			<h3 class= "itemName">クロカンブッシュケーキ</h3>
			<p class= "price">価格 ￥780(税込み)</p>
			<img class="itemImg" alt="クロカンブッシュケーキ" src="${pageContext.request.contextPath}/images/cake_shuu.png">
			<div class= "cardBottom">
			<span class= "stock">注文数</span>
			<input type= "number" name="shuuCount" min= "0" value= "0">
			<button class= "cartButton">カート追加</button>
			</div>
		</div>	
		<div class= "menuCard">
			<h3 class= "itemName">アールグレイムースケーキ</h3>
			<p class= "price">価格 ￥680(税込み)</p>
			<img class="itemImg" alt="アールグレイムースケーキ" src="${pageContext.request.contextPath}/images/cake_moose.png">
			<div class= "cardBottom">
			<span class= "stock">注文数</span>
			<input type= "number" name="mooseCount" min= "0" value= "0">
			<button class= "cartButton">カート追加</button>
			</div>
			
		</div>
		
	</div>
	</section>
	<section id= "bakes">
	
	</section>
	<section id= "pastries">
	
	</section>


<!-- 注文フォーム。一旦divで囲ってます -->
<div class= "orderArea">
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

</div>
	
	<jsp:include page="common/footer.jsp"/>
	</body>
</html>