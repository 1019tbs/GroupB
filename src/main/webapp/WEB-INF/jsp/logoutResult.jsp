<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログアウト</title>

<script>
window.onload = function() {
    setTimeout(function() {
        location.href = "${pageContext.request.contextPath}/";
    }, 3000);
};
</script>

</head>

<body>

<h2>ログアウトしました。</h2>

<p>3秒後にログイン画面へ移動します。</p>

</body>
</html>