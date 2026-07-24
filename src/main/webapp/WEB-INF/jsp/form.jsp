<!-- お問い合わせフォーム画面 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- cssファイル読み込み -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>

<body class= "contact">
<div class= "contactContainer">
	
	<div class= "contactLeft">
	<img alt="フォーム画像" src="${pageContext.request.contextPath}/images/formImg.png" class= "formImg">
	</div>

	<div class= "contactRight">
		<h1 >Honey Bloom
		<img alt="アイコン" src="${pageContext.request.contextPath}/images/icon.png" class= "icon" >
		</h1>
		<p>はちみつ香る、しあわせなお菓子時間</p>
		
		<h2 class= "formTitle">お問い合わせ</h2>
		<p>商品やご注文について、お気軽にお問い合わせください。</p>
		<form action="${pageContext.request.contextPath}/form/submit" method= "post">
		    <!-- お問い合わせを表す隠し項目 -->
		    <input type="hidden"
		           name="genre"
		           value="contact">
		    
		    <!-- 横並びに配置 -->       
		    <div class= "formRow">
		    	<div class= "formItem">
			    	<label>お名前</label>
				    <input type="text"
				           name="customerName"
				           value="${customerName}"
				           placeholder="例）山田太郎">
				</div>
				<div class= "formItem">
		    		<label>件名</label>
		    		<input type="text"
		        		   name="subject"
		        		   value="${subject}"
		        		   placeholder="例）商品について">
		        </div>		    	
		    </div>
		    <div class= "formRow">
		    	<div class= "formItem mailItem">
					<label>メールアドレス</label>
				    <input type="email"
				           name="email"
				           value="${email}"
				           placeholder="例）example@example.com">		    	
		    	</div>
		    	<div class= "formItem phoneItem">
		    		<label>電話番号</label>
		   			<input type="text"
		        		   name="phone"
		        		   value="${phone}"
		        		   placeholder="例）090-1234-5678">
		        </div>
		        		    </div>
		
		
		    <label>お問い合わせ内容</label>
		    <textarea name="message">${message}</textarea>
		
		    <p style="color:red;">
		        ${errorMsg}
		    </p>
		
			<div class= "buttonArea">
				<button class= "subButton" type="submit">送信</button>
			</div>		
		</form>
		
	</div>
	
</div>
<jsp:include page="common/footer.jsp" />
</body>
</html>