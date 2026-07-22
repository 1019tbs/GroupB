memberEditComplete_oonaka.jsp

<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>会員情報変更完了</title>
</head>
<body>

<h1>会員情報変更完了（テスト）</h1>

<p>会員情報を変更しました。</p>

<hr>

<p>
    会員ID：
    ${member.memberId}
</p>

<p>
    氏名：
    ${member.memberName}
</p>

<p>
    郵便番号：
    ${member.postalCode}
</p>

<p>
    住所：
    ${member.address}
</p>

<p>
    電話番号：
    ${member.phoneNumber}
</p>

<p>
    メールアドレス：
    ${member.email}
</p>

<p>
    支払方法：
    ${member.paymentMethod}
</p>

<hr>

<form action="${pageContext.request.contextPath}/main"
      method="get">

    <button type="submit">
        メニューへ戻る
    </button>

</form>

</body>
</html>