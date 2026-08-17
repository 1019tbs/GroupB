<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ja">

<head><link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/favicon.ico">

<meta charset="UTF-8">

<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>ログアウト</title>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/logout.css">

<script>
	window.addEventListener("load", function() {

		setTimeout(function() {

			location.href = "${pageContext.request.contextPath}/";

		}, 3000);

	});
</script>

</head>

<body>

	<main class="logout-page">

		<section class="logout-card">

			<div class="logout-image-area">

				<img class="logout-image"
					src="${pageContext.request.contextPath}/images/logoutshibahoney.png"
					alt="手を振る蜂">

			</div>

			<h1 class="logout-title">ログアウトしました</h1>

			<p class="logout-message">ご利用ありがとうございました。</p>

			<p class="redirect-message">3秒後にログイン画面へ移動します。</p>

			<div class="loading-dots" aria-hidden="true">

				<span></span> <span></span> <span></span>

			</div>

			<a class="login-link" href="${pageContext.request.contextPath}/">

				すぐにログイン画面へ戻る </a>

		</section>

	</main>

</body>

</html>