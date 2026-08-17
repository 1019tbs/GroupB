<%@ page contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>

<head><link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/favicon.ico">

<meta charset="UTF-8">

<title>ご注文ありがとうございました</title>

<!-- 共通CSS -->
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/css/style.css">

<!-- 注文完了画面専用CSS -->
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/css/thankyou.css">

</head>


<body class="thankyouPage">


    <!-- ========================
         上部オーニング
         ======================== -->
    <img class="thankyouTopAwning"
        src="${pageContext.request.contextPath}/images/edit_top.png"
        alt="">


    <!-- ========================
         Honey Bloom ロゴ
         ======================== -->
    <div class="shopTitle">

        <h1>
            Honey Bloom

            <img class="icon"
                src="${pageContext.request.contextPath}/images/icon.png"
                alt="蜂">
        </h1>

        <p>
            はちみつ香る、しあわせなお菓子時間。
        </p>

    </div>


    <!-- ========================
         右上メニュー
         ======================== -->
    <div class="shopHeaderMenu">


        <form action="${pageContext.request.contextPath}/menu"
            method="get">

            <button class="headerButton"
                type="submit">

                <img class="headerIcon"
                    src="${pageContext.request.contextPath}/images/icon_menu.png"
                    alt="メニュー">

                メニュー

            </button>

        </form>


        <span>/</span>


        <form action="${pageContext.request.contextPath}/cart"
            method="get">

            <button class="headerButton"
                type="submit">

                <img class="headerIcon"
                    src="${pageContext.request.contextPath}/images/icon_cart.png"
                    alt="カート">

                カート

            </button>

        </form>


        <span>/</span>


        <form action="${pageContext.request.contextPath}/orders/history"
            method="get">

            <button class="headerButton"
                type="submit">

                <img class="headerIcon"
                    src="${pageContext.request.contextPath}/images/icon_log.png"
                    alt="注文履歴">

                注文履歴

            </button>

        </form>


    </div>


    <!-- ========================
         メイン
         ======================== -->
    <main class="thankyouMain">


        <!-- ========================
             本＋本の中の文章
             ======================== -->
        <div class="thankyouBookArea">


            <!-- 本 -->
            <img class="thankyouBook"
                src="${pageContext.request.contextPath}/images/thankyou_book.png"
                alt="レシピブック">


            <!-- ========================
                 本の右ページ
                 ======================== -->
            <div class="thankContent">


                <h2>
                    Thank you<br>
                    for your order!
                </h2>


                <img class="line2"
                    src="${pageContext.request.contextPath}/images/line2.png"
                    alt="">


                <p class="thankText">

                    <c:choose>

                        <c:when test="${fulfillmentMethod == 'PICKUP'}">

                            店頭受取のご予約ありがとうございました。<br>

                            受取日時：
                            <c:out value="${pickupDate}" />
                            <c:out value="${pickupTime}" />

                        </c:when>


                        <c:otherwise>

                            ご注文ありがとうございました。

                        </c:otherwise>

                    </c:choose>


                    <br>

                    注文番号：
                    <c:out value="${completedOrderId}" />

                    <br>


                    <c:choose>

                        <c:when test="${mailSent}">

                            ご注文内容をメールアドレスへ送信しました。

                        </c:when>


                        <c:otherwise>

                            ご注文内容は注文履歴から確認できます。

                        </c:otherwise>

                    </c:choose>

                </p>


                <!-- 梱包画像 -->
                <img class="thankImg"
                    src="${pageContext.request.contextPath}/images/thankyou.png"
                    alt="梱包画像">


                <!-- 案内メッセージ -->
                <div class="thankWaitMessage">

                    <c:choose>

                        <c:when test="${fulfillmentMethod == 'PICKUP'}">

                            ご指定の日時に店舗へお越しください。

                        </c:when>


                        <c:otherwise>

                            Honey Bloomのお菓子が届くまで、<br>
                            もうしばらくお待ちください。

                        </c:otherwise>

                    </c:choose>

                </div>


                <!-- 戻るボタン -->
                <a class="backButton"
                    href="${pageContext.request.contextPath}/main">

                    TOPへ戻る

                </a>


            </div>

        </div>

    </main>


    <!-- フッター -->
    <jsp:include page="common/footer.jsp"/>


</body>

</html>