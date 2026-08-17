<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="ja">
<head><link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/favicon.ico">
<meta charset="UTF-8">
<meta name="viewport"
      content="width=device-width, initial-scale=1.0">

<title>会員登録完了 | Honey Bloom</title>

<link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/style.css">

<style>
body {
    margin: 0;
    background-color: #fffaf2;
    color: #4a321f;
    font-family: "Yu Gothic", "Meiryo", sans-serif;
}

.result-container {
    width: min(620px, 90%);
    margin: 80px auto;
    padding: 40px;
    background-color: #ffffff;
    border: 1px solid #e6cfaa;
    border-radius: 12px;
    box-sizing: border-box;
    text-align: center;
    box-shadow: 0 4px 12px rgba(100, 70, 30, 0.12);
}

h1 {
    margin-top: 0;
    color: #783f04;
}

.result-message {
    margin: 24px 0;
    line-height: 1.8;
}

.member-id-box {
    margin: 24px auto;
    padding: 16px;
    background-color: #fff7e8;
    border: 1px solid #e1bf87;
    border-radius: 8px;
}

.member-id {
    font-size: 1.15rem;
    font-weight: bold;
    color: #783f04;
}

.login-button {
    display: inline-block;
    min-width: 180px;
    padding: 12px 20px;
    background-color: #b66a21;
    color: #ffffff;
    border-radius: 6px;
    text-decoration: none;
}
</style>
</head>

<body>

<div class="result-container">

    <h1>会員登録が完了しました</h1>

    <p class="result-message">
        <strong>
            <c:out value="${member.memberName}" />
        </strong>
        さん、ご登録ありがとうございます。
    </p>

    <div class="member-id-box">
        <p>ログインに使用する会員ID</p>

        <p class="member-id">
            <c:out value="${member.memberId}" />
        </p>

        <p>
            会員IDは変更できません。
            忘れないように保管してください。
        </p>
    </div>

    <a href="${pageContext.request.contextPath}/"
       class="login-button">
        ログイン画面へ
    </a>

</div>

<jsp:include page="common/footer.jsp" />

</body>
</html>
