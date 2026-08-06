<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>パスワード変更</title>
</head>
<!-- CSS -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/memberEdit.css">

<body class= "passwordChange">
	<div class= "headerArea">
		<img class="headerImg" 
   		alt="Header画像"
   		src="${pageContext.request.contextPath}/images/edit_top.png">
   		<header class="HeaderBox">
        	<h1>
            <span>Honey Bloom.</span>
            <img
                src="${pageContext.request.contextPath}/images/icon.png"
                class="icon"
                alt="Honey Bloomのアイコン">
        	</h1>
        	<p class="subtitle">～ PASSWORD CHANGE ～</p>
        	<h2>パスワード変更</h2>
    	</header>
	</div>
   	<div class= "passwordContainer">
    	<img
    	src="${pageContext.request.contextPath}/images/edit_pass.png"
        class="passwordImg"
        alt="パスワード">
		<c:if test="${not empty errorMsg}">
    	<p style="color:red;">
        	<c:out value="${errorMsg}" />
    	</p>
		</c:if>

		<form action="${pageContext.request.contextPath}/member/PasswordChange"
		method="post">
	    	<p class="inputArea">
	        	現在のパスワード：<br>
	        <input type="password"
	               name="currentPassword">
	    	</p>
	    	<p class="inputArea">
	        	新しいパスワード：<br>
	        	<input type="password"
	               name="newPassword">
	    	</p>
	    	<p class="inputArea">
	        	新しいパスワード（確認）：<br>
	        	<input type="password"
	        	name="confirmPassword">
	    	</p>
		</form>
		<p>
		<button
    		type="button"
    		class="backButton"
    		onclick="location.href='${pageContext.request.contextPath}/member/edit'">
    		パスワードを変更する
		</button>		
	</div>
	<div class="backMenuArea">
	   	<a class="backMenu" href="${pageContext.request.contextPath}/main">
	    ← メニューへ戻る
	    </a>
	</div>
    <jsp:include page="common/footer.jsp" />
</body>

</html>