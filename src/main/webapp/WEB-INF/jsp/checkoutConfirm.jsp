<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<!DOCTYPE html>
<html>
<head><link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/favicon.ico">
<meta charset="UTF-8">
<title>購入内容確認</title>
<!-- CSS -->
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/css/cart.css">
</head>

<body class="checkoutPage">
	<div class= "confirmHeader">
		<img
		src="${pageContext.request.contextPath}/images/cart_logo.png"
		class="cartLogo"
		alt="ロゴ">
	    <h1>
	    	<c:choose>
	    		<c:when test="${checkoutForm.fulfillmentMethod == 'PICKUP'}">
	    		店頭受取予約情報入力
	            </c:when>
	            <c:otherwise>購入情報入力</c:otherwise>
	        </c:choose>
	    </h1>
	    <p>
	    「注文確定」を押すと、注文登録・在庫減算・カートクリアを実行します。
	    </p>
	</div>

	<main>
    	<div class="confirmContainer">
    		<div class= "confirmMain">
    		<!-- 左側 -->
	    		<div class="checkoutArea">
	    			<div class="checkoutProductList">
	    				<h2>1.購入予定の商品</h2>
	    				<table class="cartTable">
			    			<thead>
			    			<tr>
			    				<th>商品名</th>
			    				<th>単価</th>
				                <th>数量</th>
				                <th>小計</th>
				            </tr>
					        </thead>
					        <tbody>
				                <c:forEach var="item"
				                items="${cartList}">
				                <tr>
				                	<td class="productCell">
				                    <img
				                    class="checkoutProductImg"
				                    src="${pageContext.request.contextPath}${item.product.imageUrl}"
					                alt="<c:out value='${item.product.productName}' />">
				                    	<span>
				                        		<c:out value="${item.product.productName}" />
				                        </span>
									</td>
				                	<td>
				                		<fmt:formatNumber
				                		value="${item.product.price}"
				                 		pattern="#,##0" />円
				                	</td>
				                	<td>
				                		${item.quantity}
				                	</td>
				                	<td>
				                		<fmt:formatNumber
				                		value="${item.subtotal}"
				                		pattern="#,##0" />円
				                	</td>
				                </tr>
				                </c:forEach>
			                </tbody>
			            </table>
			                <p class="checkoutTotal">
			                	合計：
			                	<strong>
			                		<fmt:formatNumber
			                		value="${total}"
			                    	pattern="#,##0" />
			                    	円
			                    </strong>
			                </p>	
			    		</div>
			    	<!-- 注文者情報 -->
			    	<div class= "checkoutForm">
			    	<div class="checkoutStep active">
    					<h2>2.お届け先・ご注文者情報</h2>
    				</div>
	    			<div class="formGroup">
	    				<dl>
<!--				            <dt>受取方法</dt>-->
<!--	            			<dd><c:out value="${fulfillmentMethodLabel}" /></dd>-->
	            			<dt>氏名</dt>
	            			<dd>
	            				<c:out
	                    		value="${checkoutForm.customerName}" />
	                    	</dd>
	                    	<c:if test="${checkoutForm.fulfillmentMethod == 'DELIVERY'}">
				                <dt>郵便番号</dt>
				                <dd>
				                    <c:out value="${checkoutForm.postalCode}" />
				                </dd>
				
				                <dt>住所</dt>
				                <dd>
				                    <c:out value="${checkoutForm.address}" />
				                </dd>
				            </c:if>
				            <c:if test="${checkoutForm.fulfillmentMethod == 'PICKUP'}">
				                <dt>受取希望日</dt>
				                <dd><c:out value="${checkoutForm.pickupDate}" /></dd>
				
				                <dt>受取希望時間</dt>
				                <dd><c:out value="${checkoutForm.pickupTime}" /></dd>
				            </c:if>
				            <dt>電話番号</dt>
				            <dd>
				                <c:out
				                value="${checkoutForm.phone}" />
				            </dd>
				            <dt>メールアドレス</dt>
				            <dd>
				                <c:out
				                value="${checkoutForm.email}" />
				            </dd>
				            <dt>支払方法</dt>
				            <dd>
				                <c:out
				                value="${paymentMethodLabel}" />
				            </dd>
				        </dl>
	    			</div>

				 	</div>
				    <div class="checkoutActions">
				        <a href="${pageContext.request.contextPath}/checkout/input"
				        class= "modifyButton">
				            入力内容を修正する
				        </a>
				        <a href="${pageContext.request.contextPath}/cart"
				        class="backButton">
				            カートに戻る
				        </a>
				
				        <form
					        id="completeForm"
				            action="${pageContext.request.contextPath}/checkout/complete"
				            method="post"
				            onsubmit="this.querySelector('button').disabled = true;">
				
				            <input type="hidden"
				                name="checkoutToken"
				                value="${checkoutToken}">
				            <input type="hidden"
				                name="cartSignature"
				                value="${fn:escapeXml(cartSignature)}">
				            <input type="hidden"
				                name="customerName"
				                value="${fn:escapeXml(checkoutForm.customerName)}">
				            <input type="hidden"
				                name="postalCode"
				                value="${fn:escapeXml(checkoutForm.postalCode)}">
				            <input type="hidden"
				                name="address"
				                value="${fn:escapeXml(checkoutForm.address)}">
				            <input type="hidden"
				                name="phone"
				                value="${fn:escapeXml(checkoutForm.phone)}">
				            <input type="hidden"
				                name="email"
				                value="${fn:escapeXml(checkoutForm.email)}">
				            <input type="hidden"
				                name="paymentMethod"
				                value="${fn:escapeXml(checkoutForm.paymentMethod)}">
				            <input type="hidden"
				                name="fulfillmentMethod"
				                value="${fn:escapeXml(checkoutForm.fulfillmentMethod)}">
				            <input type="hidden"
				                name="pickupDate"
				                value="${fn:escapeXml(checkoutForm.pickupDate)}">
				            <input type="hidden"
				                name="pickupTime"
				                value="${fn:escapeXml(checkoutForm.pickupTime)}">
				        </form>
			        </div>
			    	
			    	</div>
				    <!-- ご注文内容 -->
		    		<div class= "cartSummary">
		            	<img
		            	src="${pageContext.request.contextPath}/images/cart_check.png"
		            	class="summaryImg"
		            	alt="購入イメージ">
				        <div class= "summaryBox">
				        	<h2 class= "summaryTitle">ご注文確認</h2>
				        	<div class= "summaryInfo">
				        		<p>カート内商品　
	                				<strong>${cartList.size()}</strong>種類
	                			</p>
	                			<p class= "summaryInfoNumber">購入数量
	                				<strong>${totalQuantity}</strong>点
	                			</p>
	                		</div>
	                		<div class= "summaryTotal">
	                			<span>
			                	合計：
				            	</span>
				            	<strong>
				            		<fmt:formatNumber value="${total}" pattern="#,##0" />
				                	円
				            	</strong>
	                		</div>
				        </div>
				        <div class="summaryButtonArea">
				        	<button 
				        	type="submit" 
				        	class="orderButton"
				        	form="completeForm">
				        	注文確定
				        	</button>
				        </div>
				        <div class="cartBack">
				        	<a href="${pageContext.request.contextPath}/menu">
				        	← 商品一覧に戻る
				        	</a>
				        </div>
			            	<c:if test="${not canPurchase}">
			                <p class="errorMsg">
			                    取扱停止・在庫切れ・在庫不足の商品があります。
			                    数量を修正するか、商品を取り消してください。
			                </p>
			            	</c:if>
		                    <img
					        src="${pageContext.request.contextPath}/images/cart_check2.png"
					        class="summaryImg"
					        alt="購入イメージ">
					</div>
	        </div>
	    </div>

</main>
	<div class= "cartFooterArea">
		<img
		src="${pageContext.request.contextPath}/images/menu_footer2.png"
		class="cartFooterImg"
		alt="カート下背景">
	</div>
	<jsp:include page="common/footer.jsp" />
</body>
</html>