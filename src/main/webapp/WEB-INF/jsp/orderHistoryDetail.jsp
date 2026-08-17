<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">

<title>注文履歴詳細</title>

<link rel="stylesheet"
    href="${pageContext.request.contextPath}/css/style.css">

<link rel="stylesheet"
    href="${pageContext.request.contextPath}/css/orderHistoryDetail.css">

</head>

<body class="orderHistoryDetailPage">

<jsp:include page="/WEB-INF/jsp/common/header.jsp" />

<main class="orderHistoryDetailContainer">


    <section class="orderHistoryDetailHero">

        <img class="orderHistoryDetailHeroImg"
            src="${pageContext.request.contextPath}/images/orderHistory_title.png"
            alt="">

        <div class="orderHistoryDetailHeroOverlay">

            <h1>ご注文履歴詳細</h1>

            <span class="detailHeroLine"></span>

        </div>

    </section>


    <section class="orderHistoryDetailCard">


        <div class="orderDetailTop">

            <div class="orderDetailBasic">

                <h2>
                    注文番号：
                    <c:out value="${order.shoppingOrderId}" />
                </h2>

                <p class="orderDetailMethod">
                    <c:out value="${order.fulfillmentMethodLabel}" />
                </p>

            </div>

            <span class="orderDetailStatus">
                <c:out value="${order.orderStatusLabel}" />
            </span>

        </div>


        <div class="orderDetailDateArea">

            <p>
                <span class="detailLabel">受付日時：</span>

                <c:out value="${order.createdAtText}" />
            </p>

            <c:if test="${order.pickup}">

                <p>
                    <span class="detailLabel">受取日時：</span>

                    <c:out value="${order.pickupDateText}" />
                    <c:out value="${order.pickupTimeText}" />
                </p>

            </c:if>

        </div>


        <section class="orderDetailSection">

            <h3>購入商品</h3>

            <div class="orderDetailTableArea">

                <table class="orderDetailTable">

                    <thead>
                        <tr>
                            <th class="productNameColumn">
                                商品名
                            </th>

                            <th>
                                単価
                            </th>

                            <th>
                                数量
                            </th>

                            <th>
                                小計
                            </th>
                        </tr>
                    </thead>

                    <tbody>

                        <c:forEach var="item"
                            items="${order.items}">

                            <tr>

                                <td class="productNameCell">
                                    <c:out value="${item.productName}" />
                                </td>

                                <td>
                                    <fmt:formatNumber
                                        value="${item.unitPrice}"
                                        pattern="#,##0" />円
                                </td>

                                <td>
                                    ${item.quantity}個
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

            </div>


            <p class="orderDetailTotal">

                合計：

                <strong>
                    <fmt:formatNumber
                        value="${order.totalAmount}"
                        pattern="#,##0" />円
                </strong>

            </p>

        </section>


        <section class="orderDetailSection customerInfoSection">

            <h3>注文者情報</h3>

            <dl class="customerInfoList">

                <div class="customerInfoRow">
                    <dt>氏名</dt>

                    <dd>
                        <c:out value="${order.customerName}" />
                    </dd>
                </div>


                <c:if test="${order.delivery}">

                    <div class="customerInfoRow">
                        <dt>郵便番号</dt>

                        <dd>
                            <c:out value="${order.postalCode}" />
                        </dd>
                    </div>


                    <div class="customerInfoRow">
                        <dt>住所</dt>

                        <dd>
                            <c:out value="${order.address}" />
                        </dd>
                    </div>

                </c:if>


                <div class="customerInfoRow">
                    <dt>電話番号</dt>

                    <dd>
                        <c:out value="${order.phone}" />
                    </dd>
                </div>


                <div class="customerInfoRow">
                    <dt>メールアドレス</dt>

                    <dd>
                        <c:out value="${order.email}" />
                    </dd>
                </div>


                <div class="customerInfoRow">
                    <dt>支払方法</dt>

                    <dd>
                        <c:out value="${order.paymentMethodLabel}" />
                    </dd>
                </div>

            </dl>

        </section>


    </section>


    <div class="orderHistoryDetailActions">

        <a href="${pageContext.request.contextPath}/orders/history">
            注文履歴一覧へ戻る
        </a>

        <a href="${pageContext.request.contextPath}/menu">
            メニューへ戻る
        </a>

    </div>


</main>

</body>

</html>