<!-- ログイン画面 -->

<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head><link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/favicon.ico">
<meta charset="UTF-8">
<title>Honey Bloom</title>

<!-- CSSファイル読み込み -->
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/css/style.css">
</head>

<body class="loginPage">

    <!-- ログイン画面上部のテント画像 -->
    <div class="loginTopImg"></div>

    <div class="loginContents">

        <!-- ログインカード -->
        <div class="loginCard">

            <h1>
                Honey Bloom
                <img class="icon"
                    src="${pageContext.request.contextPath}/images/icon.png"
                    alt="アイコン">
            </h1>

            <p>はちみつ香る、しあわせなお菓子時間</p>

            <form action="Login" method="post">

                <label>会員ID</label>
                <input type="text"
                    name="name"
                    placeholder="会員IDを入力">

                <label>パスワード</label>
                <input type="password"
                    name="pass"
                    placeholder="パスワードを入力">

                <input type="submit" value="ログイン">

            </form>

            <div class="or">または</div>

            <form action="${pageContext.request.contextPath}/registration"
                method="get">

                <button type="submit">新規会員登録はこちら</button>

            </form>

        </div>

    </div>

    <jsp:include page="common/footer.jsp" />

</body>
</html>