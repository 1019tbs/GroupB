<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head><link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/favicon.ico">
<meta charset="UTF-8">
<title>パスワード変更完了</title>
</head>
<!-- CSS -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/memberEdit.css">

<body class= "passwordChangeComplete">
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
        		<p class="subtitle">～ PASSWORD INFORMATION ～</p>
		</header>
	</div>
	<div class= "completeContainer">
		<!-- 左側 -->
		<div class = "completeLeft">
			<img src="${pageContext.request.contextPath}/images/edit_pass_comp.png"
			class="editPassCompImg"
	        alt="パスワード変更完了">
		</div>
		<!-- 右側 -->
		<div class ="completeRight">
			<h2>パスワードを変更しました。</h2>
			<img class="lineImg"  
			alt="ライン" 
			src="${pageContext.request.contextPath}/images/line1.png">
			<p>
			ご登録内容を更新しました。<br>
			引き続き、HoneyBloomでのお買い物をお楽しみください。
			</p>
			
			<form action="${pageContext.request.contextPath}/main"
			method="get">
	    		<button class= "backButton" type="submit">
	        		メニューへ戻る
	    		</button>
	    	</form>
		</div>
	</div>
	<jsp:include page="common/footer.jsp" />
</body>
</html>