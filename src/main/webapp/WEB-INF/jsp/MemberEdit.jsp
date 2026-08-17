<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html>
<head><link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/favicon.ico">
<meta charset="UTF-8">
<title>会員情報変更</title>
<!-- cssファイル読み込み -->
<!--<link rel="stylesheet"-->
<!--	href="${pageContext.request.contextPath}/css/style.css">-->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/memberEdit.css">
</head>

<body class="memberEditPage">
	<jsp:include page="common/header.jsp" />
	<h1>会員情報変更</h1>
	<c:if test="${not empty errorMsg}">
		<p style="color: red;">
			<c:out value="${errorMsg}" />
		</p>
	</c:if>

	<form class= "memberEditForm"
		action="${pageContext.request.contextPath}/member/edit"
		method="post">

		<p>
			会員ID：
			<c:out value="${member.memberId}" />
		</p>

		<input type="hidden" name="memberId" value="${member.memberId}">

		<p>
			<span class= "label">氏名：</span> 
			<input type="text" name="memberName" value="${member.memberName}">
		</p>

		<p>
			<span class= "label">郵便番号：</span>
			<input type="text" id="postalCode" name="postalCode"
				value="${member.postalCode}">
			<button type="button" class="address-button" id="addressSearchButton"
				onclick="searchAddress()">住所検索</button>
		</p>

		<p>
			<span class= "label">住所：</span> 
			<input type="text" id="address" name="address"
				value="${member.address}">
		</p>

		<p>
			<span class= "label">電話番号：</span>
			<input type="text" name="phoneNumber"
				value="${member.phoneNumber}">
		</p>

		<p>
			<span class= "label">生年月日：</span>
			<input type="date" name="birthDate" value="${member.birthDate}">
		</p>

		<p>
			<span class= "label">メールアドレス：</span>
			<input type="email" name="email" value="${member.email}">
		</p>

		<p>
			<span class= "label">支払方法：</span>
			<select name="paymentMethod">
				<option value="CREDIT"
					<c:if test="${member.paymentMethod == 'CREDIT'}">
                    selected
                	</c:if>>
					クレジットカード
				</option>

				<option value="COD"
					<c:if test="${member.paymentMethod == 'COD'}">
                    selected
                	</c:if>>
					代金引換
				</option>

				<option value="BANK"
					<c:if test="${member.paymentMethod == 'BANK'}">
                    selected
                	</c:if>>
					銀行振込
				</option>
			</select>
		</p>

		<div class="memberButtonArea">

			<button type="button"
				onclick="location.href='${pageContext.request.contextPath}/member/PasswordChange'">
				パスワード変更画面</button>
			<button type="submit">会員情報を変更する</button>
		</div>

	</form>

	<!-- <form action="${pageContext.request.contextPath}/member/password"
      method="get">

    <button type="submit">
        パスワード変更はこちら
    </button>

</form> -->


	<script>
function searchAddress() {
    const postalCode = document.getElementById("postalCode").value;
    
    if (!postalCode) {
        alert("郵便番号を入力してください。");
        return;
    }

    fetch('${pageContext.request.contextPath}/member/search-address?postalCode=' + encodeURIComponent(postalCode))
        .then(response => response.text())
        .then(address => {
            if (address) {
                document.getElementById("address").value = address;
            } else {
                alert("該当する住所が見つかりませんでした。");
            }
        })
        .catch(error => {
            console.error("エラーが発生しました:", error);
            alert("住所の取得に失敗しました。");
        });
}
</script>

	<jsp:include page="common/footer.jsp" />
</body>
</html>