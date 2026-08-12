<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ショッピングカート</title>
<!-- CSS -->
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/css/cart.css">
</head>

<body class="cartPage">
	<header class="cartHeader">
		<img
		src="${pageContext.request.contextPath}/images/cart_logo.png"
		class="cartLogo"
		alt="ロゴ">
	
	    <h1 class="cartTitle">SHOPPING CART</h1>
	    <p class= "subTitle">ご注文内容をご確認ください。</p>
	    <img
		src="${pageContext.request.contextPath}/images/cart_line.png"
		class="cartLine"
		alt="ヘッダー装飾">
	    
    </header>
    
	<main class="cartContainer">
	<%-- 
	    <c:if test="${not empty fulfillmentMethod}">
	        <p class="cartFulfillmentMethod">
	            受取方法：
	            <strong>
	                <c:choose>
	                    <c:when test="${fulfillmentMethod == 'PICKUP'}">
	                        店頭受取
	                    </c:when>
	                    <c:otherwise>通販</c:otherwise>
	                </c:choose>
	            </strong>
	        </p>
	    </c:if>
	    --%>
	    <c:if test="${not empty cartMessage}">
	        <p class="successMessage">
	            <c:out value="${cartMessage}" />
	        </p>
	    </c:if>
	    <c:if test="${not empty cartErrorMessage}">
	        <p class="errorMsg">
	            <c:out value="${cartErrorMessage}" />
	        </p>
	    </c:if>
	    <c:choose>
	        <c:when test="${empty cartList}">
	        	<div class= "emptyCart">
	        			<p class= "emptyMessage">カートに商品がありません。</p>
	        				<a href="${pageContext.request.contextPath}/menu" 
	            			class= "backButton">
	                		メニューへ戻る
	            			</a>
	            </div>			
	        </c:when>
	        <c:otherwise>
	        <c:set var="total" value="0" />
	        <c:set var="canPurchase" value="true" />
	        <!-- 左側 -->
	            <div class="cartMain">
	            	<div class= "cartList">
	                    <c:forEach var="item" items="${cartList}">
		                    <!-- 合計 -->
		                        <c:set var="total" value="${total + item.subtotal}" />
							<!-- 購入可否 -->
						    <c:if test="${not item.product.active
						            or item.product.stock <= 0
						            or item.quantity > item.product.stock
						            or (fulfillmentMethod == 'DELIVERY'
						            and not item.product.deliveryAvailable)
						            or (fulfillmentMethod == 'PICKUP'
						            and not item.product.pickupAvailable)}">
						    	<c:set var="canPurchase" value="false"/>
						    </c:if>
		            		<div class= "cartItem">
			            		 <div class= "cartImg">
			            		 	<c:if test="${not empty item.product.imageUrl}">
			                        <img
			                            src="${pageContext.request.contextPath}${item.product.imageUrl}"
			                            alt="<c:out value='${item.product.productName}' />">
			                    	</c:if>
			            		 </div>
			            		 <div class= "itemInfo">
				            		<h3 class="productName">
				                    	商品名：
				                    	<c:out value="${item.product.productName}" />
				                    </h3>
				                    <p>
				                    	販売価格：
				                    	<fmt:formatNumber
				                       	value="${item.product.price}"
				                        pattern="#,##0"/>円（税込）
				                   	</p>

									<div class="stockStatus">
									    <c:choose>
									        <c:when test="${item.product.stock <= 0}">
									            <span class="statusNg">
									                購入不可
									            </span>
									        </c:when>
									
									        <c:otherwise>
									            <span class="statusOk">
									                購入可能
									            </span>
									        </c:otherwise>
									
									    </c:choose>
									</div>
									
									<div class= "productStock quantityArea">
				                    	<!-- 数量変更フォーム -->
				                    	<form
				                    	class= "stockForm"
				                        action="${pageContext.request.contextPath}/cart/update"
				                        method="post">
				                        <input type="hidden"
				                               name="productId"
				                               value="${item.product.productId}">
				                        数量
				                        <input
				                            type="number"
				                            name="quantity"
				                            value="${item.quantity}"
				                            min="1"
				                            max="${item.product.stock}">
				                        <button class= "changeButton" type="submit">変更</button>
				                    	</form>
				                    	<div class= "stockCount">
				                    		在庫数：${item.product.stock}
				                    	</div>
									</div>
									
				            		<div class="itemBottom">
				                        <div>
				                            小計：
				                            <fmt:formatNumber
				                                value="${item.subtotal}"
				                                pattern="#,##0"/>
				                            円
				                        </div>
				                        <form
				                            action="${pageContext.request.contextPath}/cart/remove"
				                            method="post">
				                            <input
				                            type="hidden"
				                            name="productId"
				                            value="${item.product.productId}">
				                            <button class= "removeButton" type="submit">取消</button>
				                        </form>
				                    </div>
			            		</div>
		            		</div>
	            		</c:forEach>
	            		<div class="checkoutBackButtons">
				    		<a href="${pageContext.request.contextPath}/menu"
				    		class="backButton">
				        	メニューへ戻る
				    	</a>
	            		</div>
	            		
	            	</div>	            
	            <!-- ご注文内容 -->
	                <div class= "cartSummary">
	                <img
			        src="${pageContext.request.contextPath}/images/cart_check.png"
			        class="summaryImg"
			        alt="購入イメージ">
			        <div class= "summaryBox">
			        	<h2 class= "summaryTitle">ご注文内容</h2>
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
                		<!-- 購入ボタン -->
                		<div class="purchaseArea">
						    <c:choose>
						        <c:when test="${canPurchase}">
						            <a class="purchaseButton"
						               href="${pageContext.request.contextPath}/checkout/input">
						                <c:choose>
						                    <c:when test="${fulfillmentMethod == 'PICKUP'}">
						                        店頭受取を予約する
						                    </c:when>
						                    <c:otherwise>
						                        購入する
						                    </c:otherwise>
						                </c:choose>
						            </a>
						        </c:when>
						
						        <c:otherwise>
						            <button class="purchaseButtonDisabled" disabled>
						                購入する
						            </button>
						        </c:otherwise>
						    </c:choose>
						</div>
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
		            	<%-- 
			            <div class="cartActions">
			                <a href="${pageContext.request.contextPath}/menu">
			                    商品一覧に戻る
			                </a>
			
			                <c:choose>
			                    <c:when test="${canPurchase}">
			                        <a href="${pageContext.request.contextPath}/checkout/input">
			                            <c:choose>
			                                <c:when test="${fulfillmentMethod == 'PICKUP'}">
			                                    店頭受取を予約する
			                                </c:when>
			                                <c:otherwise>購入する</c:otherwise>
			                            </c:choose>
			                        </a>
			                    </c:when>
			
			                    <c:otherwise>
			                        <button type="button" disabled>
			                            注文手続きへ
			                        </button>
			                    </c:otherwise>
			                </c:choose>
		            	</div>
		            	--%>
                    <img
			        src="${pageContext.request.contextPath}/images/cart_check2.png"
			        class="summaryImg"
			        alt="購入イメージ">
		            	
		            </div>
	        		</c:otherwise>
	    		</c:choose>
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
