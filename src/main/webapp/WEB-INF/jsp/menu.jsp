<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Menu</title>
<!-- cssファイル読み込み -->
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/css/style.css">
</head>

<body class="menu">
<jsp:include page="common/header.jsp"/>
    <img class="menuTopImg" alt="メニュー上部画像"
        src="${pageContext.request.contextPath}/images/menu_top2.png">

    <div class="menuHeader">
        <nav class="menuNav">
            <a href="#cakes">CAKES</a>
            /
            <a href="#bakes">BAKES</a>
            /
            <a href="#pastries">PASTRIES</a>
            /
            <a href="${pageContext.request.contextPath}/cart">
                ショッピングカート
            </a>
            /
           <a href="${pageContext.request.contextPath}/orders/history">
                注文履歴
           </a>
            
        </nav>
    </div>

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

    <img class="menuNextImg" alt="今週のベイク便り"
        src="${pageContext.request.contextPath}/images/menu_next.png">

    <!-- CAKES -->
    <section id="cakes">
        <img class="categoryImg categoryCakesImg" alt="CAKES"
            src="${pageContext.request.contextPath}/images/category_cake.png">

        <div class="menuGrid">
            <c:forEach var="product" items="${productList}">
                <c:if test="${product.categoryId == 1}">
                    <div class="menuCard">
                        <h3 class="itemName">
                            <c:out value="${product.productName}" />
                        </h3>

                        <p class="price">
                            価格 ￥<fmt:formatNumber
                                value="${product.price}"
                                pattern="#,##0" />(税込み)
                        </p>

                        <img class="itemImg"
                            alt="<c:out value='${product.productName}' />"
                            src="${pageContext.request.contextPath}${product.imageUrl}">

                        <c:if test="${not empty product.description}">
                            <p class="description">
                                <c:out value="${product.description}" />
                            </p>
                        </c:if>

                        <form class="cardBottom"
                            action="${pageContext.request.contextPath}/cart/add"
                            method="post">

                            <input type="hidden"
                                name="productId"
                                value="${product.productId}">

                            <input type="hidden"
                                name="fulfillmentMethod"
                                value="${product.deliveryAvailable ? 'DELIVERY' : 'PICKUP'}">

                            <span class="stock">注文数</span>

                            <input type="number"
                                name="quantity"
                                min="1"
                                max="${product.stock}"
                                value="1"
                                <c:if test="${product.stock <= 0}">disabled</c:if>>

                            <c:choose>
                                <c:when test="${product.stock > 0}">
                                    <button type="submit"
                                        class="cartButton">
                                        カート追加
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <button type="button"
                                        class="cartButton"
                                        disabled>
                                        売り切れ
                                    </button>
                                </c:otherwise>
                            </c:choose>
                        </form>
                    </div>
                </c:if>
            </c:forEach>
        </div>

        <img class="bee beeLeft" alt="蜂"
            src="${pageContext.request.contextPath}/images/hach_line.png">
    </section>

    <!-- BAKES -->
    <section id="bakes">
        <img class="categoryImg categoryBakesImg" alt="BAKES"
            src="${pageContext.request.contextPath}/images/category_bakes.png">

        <div class="bakesList">
            <c:forEach var="product" items="${productList}">
                <c:if test="${product.categoryId == 2}">
                    <div class="bakesCard">
                        <img class="bakesImg"
                            alt="<c:out value='${product.productName}' />"
                            src="${pageContext.request.contextPath}${product.imageUrl}">

                        <div class="bakesInfo">
                            <h3 class="itemName">
                                <c:out value="${product.productName}" />
                            </h3>

                            <div class="englishRow">
                                <c:choose>
                                    <c:when test="${product.imageUrl == '/images/bakes_classic.png'}">
                                        <p class="englishName">CLASSIC BAKE BOX</p>
                                        <span class="pieces">12 PIECES</span>
                                    </c:when>
                                    <c:when test="${product.imageUrl == '/images/bakes_fruity.png'}">
                                        <p class="englishName">FRUITY TEA TIME BOX</p>
                                        <span class="pieces">8 PIECES</span>
                                    </c:when>
                                    <c:when test="${product.imageUrl == '/images/bakes_nuts.png'}">
                                        <p class="englishName">HONEY &amp; NUTS BAKE BOX</p>
                                        <span class="pieces">8 PIECES</span>
                                    </c:when>
                                    <c:otherwise>
                                        <p class="englishName">HONEY BLOOM BAKE BOX</p>
                                        <span class="pieces">BAKES</span>
                                    </c:otherwise>
                                </c:choose>
                            </div>

                            <p class="description">
                                <c:out value="${product.description}" />
                            </p>

                            <p class="price">
                                ￥<fmt:formatNumber
                                    value="${product.price}"
                                    pattern="#,##0" />(税込み)
                            </p>

                            <form class="cardBottom"
                                action="${pageContext.request.contextPath}/cart/add"
                                method="post">

                                <input type="hidden"
                                    name="productId"
                                    value="${product.productId}">

                                <input type="hidden"
                                    name="fulfillmentMethod"
                                    value="${product.deliveryAvailable ? 'DELIVERY' : 'PICKUP'}">

                                <span>注文数</span>

                                <input type="number"
                                    name="quantity"
                                    min="1"
                                    max="${product.stock}"
                                    value="1"
                                    <c:if test="${product.stock <= 0}">disabled</c:if>>

                                <c:choose>
                                    <c:when test="${product.stock > 0}">
                                        <button type="submit"
                                            class="cartButton">
                                            カート追加
                                        </button>
                                    </c:when>
                                    <c:otherwise>
                                        <button type="button"
                                            class="cartButton"
                                            disabled>
                                            売り切れ
                                        </button>
                                    </c:otherwise>
                                </c:choose>
                            </form>
                        </div>
                    </div>
                </c:if>
            </c:forEach>
        </div>
    </section>

    <!-- PASTRIES -->
    <section id="pastries">
        <img class="categoryImg categoryPastriesImg" alt="PASTRIES"
            src="${pageContext.request.contextPath}/images/category_pastries.png">

        <div class="menuGrid">
            <c:forEach var="product" items="${productList}">
                <c:if test="${product.categoryId == 3}">
                    <div class="menuCard">
                        <h3 class="itemName">
                            <c:out value="${product.productName}" />
                        </h3>

                        <p class="price">
                            価格 ￥<fmt:formatNumber
                                value="${product.price}"
                                pattern="#,##0" />(税込み)
                        </p>

                        <img class="itemImg"
                            alt="<c:out value='${product.productName}' />"
                            src="${pageContext.request.contextPath}${product.imageUrl}">

                        <c:if test="${not empty product.description}">
                            <p class="description">
                                <c:out value="${product.description}" />
                            </p>
                        </c:if>

                        <form class="cardBottom"
                            action="${pageContext.request.contextPath}/cart/add"
                            method="post">

                            <input type="hidden"
                                name="productId"
                                value="${product.productId}">

                            <input type="hidden"
                                name="fulfillmentMethod"
                                value="${product.deliveryAvailable ? 'DELIVERY' : 'PICKUP'}">

                            <span class="stock">注文数</span>

                            <input type="number"
                                name="quantity"
                                min="1"
                                max="${product.stock}"
                                value="1"
                                <c:if test="${product.stock <= 0}">disabled</c:if>>

                            <c:choose>
                                <c:when test="${product.stock > 0}">
                                    <button type="submit"
                                        class="cartButton">
                                        カート追加
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <button type="button"
                                        class="cartButton"
                                        disabled>
                                        売り切れ
                                    </button>
                                </c:otherwise>
                            </c:choose>
                        </form>
                    </div>
                </c:if>
            </c:forEach>
        </div>

        <img class="bee beeRight" alt="蜂"
            src="${pageContext.request.contextPath}/images/hach_line.png">
    </section>

    <!-- 店頭受取予約は、共通カート・チェックアウトへ接続します。 -->
    <form action="${pageContext.request.contextPath}/pickup/start"
        method="post">

        <section id="reservation" class="orderArea">
            <img class="lineImg" alt="ライン"
                src="${pageContext.request.contextPath}/images/line1.png">

            <h2 class="reservationTitle">ご予約情報入力</h2>

            <p class="reservationMessage">
                気になるメニューが決まりましたら、こちらから受け取り日時をご予約ください。
            </p>

            <div class="reservationFormGrid">
                <div class="reservationFormGroup">
                    <label for="customerName">お名前</label>
                    <input id="customerName"
                        type="text"
                        name="customerName"
                        value="${param.customerName}"
                        required
                        placeholder="例）山田 太郎">

                    <!-- 店頭受取可能な商品のみ選択できます。 -->
                    <label for="menuId">予約商品</label>
                    <select id="menuId" name="productId" required>
                        <option value="">商品を選択してください</option>

                        <c:forEach var="product"
                            items="${productList}">

                            <c:if test="${product.stock > 0
                                    and product.pickupAvailable}">
                                <option value="${product.productId}"
                                    <c:if test="${param.productId == product.productId}">
                                        selected
                                    </c:if>>
                                    <c:out value="${product.productName}" />
                                </option>
                            </c:if>
                        </c:forEach>
                    </select>

                    <input type="hidden"
                        name="quantity"
                        value="1">
                </div>

                <div class="reservationFormGroup">
                    <label for="reservationDate">
                        受取希望日
                    </label>

                    <input id="reservationDate"
                        type="date"
                        name="reservationDate"
                        value="${param.reservationDate}"
                        required>

                    <label for="reservationTime">
                        受取希望時間
                    </label>

                    <input id="reservationTime"
                        type="time"
                        name="reservationTime"
                        value="${param.reservationTime}"
                        required>
                </div>

                <div class="reservationFormGroup">
                    <label for="email">
                        メールアドレス
                    </label>

                    <input id="email"
                        type="email"
                        name="email"
                        value="${param.email}"
                        required
                        placeholder="例）example@example.com">
                </div>

                <div class="reservationFormGroup">
                    <label for="phone">
                        電話番号
                    </label>

                    <input id="phone"
                        type="text"
                        name="phone"
                        value="${param.phone}"
                        required
                        placeholder="例）090-1234-5678">
                </div>
            </div>

            <p class="errorMsg">
                <c:out value="${errorMsg}" />
            </p>

            <button type="submit">予約内容を確認する</button>
        </section>
    </form>
	<!-- フローティングカート -->
	<a class="floatingCart"
	   	href="${pageContext.request.contextPath}/cart">
	    <img src="${pageContext.request.contextPath}/images/viewcart.png"
	         alt="カートを見る">
	</a>
    <footer>
        <img class="menuFooterImg" alt="メニューフッター画像"
            src="${pageContext.request.contextPath}/images/menu_footer2.png">
    </footer>
	<script src="${pageContext.request.contextPath}/JS/Cart.js"></script>
    <jsp:include page="common/footer.jsp"/>
</body>
</html>