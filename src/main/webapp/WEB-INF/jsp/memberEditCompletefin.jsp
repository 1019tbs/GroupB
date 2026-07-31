<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="ja">

<head>
<meta charset="UTF-8">

<meta
    name="viewport"
    content="width=device-width, initial-scale=1.0">

<title>会員情報変更完了 | Honey Bloom</title>

<link
    rel="stylesheet"
    href="${pageContext.request.contextPath}/css/style.css">

<style>

/* =========================
   ページ全体
========================= */
html,
body {
    width: 100%;
    min-height: 100%;
    margin: 0;
}

body {
    min-height: 100vh;
    background-color: #fffaf2;
    color: #7a4a00;

    font-family:
        "Yu Gothic",
        "Meiryo",
        sans-serif;
}

/*
 * 画面全体を使用する外側の枠
 */
.completion-page {
    width: 100%;
    min-height: 100vh;
    margin: 0;

    background-color: #fffaf2;
    box-sizing: border-box;

    display: flex;
    flex-direction: column;
}


/* =========================
   上部ヘッダー
========================= */
.completion-header {
    position: relative;

    width: 100%;
    height: 85px;

    text-align: center;

    background-image:
        url("${pageContext.request.contextPath}/images/edit_top.png");

    background-repeat: no-repeat;
    background-position: center top;
    background-size: 100% 85px;

    box-sizing: border-box;
}

.brand-area {
    display: flex;
    justify-content: center;
    align-items: center;

    padding-top: 8px;
}

.brand-name {
    color: #783f04;

    font-family:
        Georgia,
        "Times New Roman",
        serif;

    font-size: 29px;
    font-weight: bold;
    letter-spacing: 1px;
}

.brand-icon {
    width: 43px;
    height: 43px;

    margin-left: 4px;

    object-fit: contain;
}

.header-subtitle {
    margin: -2px 0 0;

    color: #a9854e;

    font-family:
        Georgia,
        "Times New Roman",
        serif;

    font-size: 14px;
}


/* =========================
   メイン部分
========================= */
.completion-main {
    flex: 1;

    width: 100%;
    padding: 8px 0 0;

    box-sizing: border-box;
}

.completion-title {
    margin: 0;

    color: #783f04;

    text-align: center;
    font-size: 17px;
}

.title-line {
    display: block;

    width: 280px;
    max-width: 80%;
    height: 7px;

    margin: 0 auto 4px;

    object-fit: contain;
}


/* =========================
   画像と会員情報
========================= */
.completion-content {
    display: flex;
    justify-content: center;
    align-items: flex-start;

    gap: 0;

    width: 580px;
    max-width: calc(100% - 20px);

    margin: 0 auto;
}

.completion-image-area {
    flex: 0 0 320px;

    width: 320px;
    min-width: 0;
}

.completion-image {
    display: block;

    width: 320px;
    max-width: 100%;
    height: 176px;

    border-radius: 2px;

    object-fit: cover;
}


/* =========================
   会員情報の枠
========================= */
.member-information {
    width: 260px;
    min-height: 176px;

    padding: 8px 14px;

    background-color: #fff0e3;

    border:
        2px solid
        rgba(253, 142, 188, 0.70);

    border-radius: 0 0 15px 15px;

    box-sizing: border-box;
}

.information-title {
    margin: 0 0 5px;

    color: #783f04;

    text-align: center;
    font-size: 14px;
    font-weight: normal;
    line-height: 1.2;
}

.information-row {
    display: grid;

    grid-template-columns: 96px 1fr;
    column-gap: 7px;

    margin-bottom: 0;

    font-size: 12px;
    line-height: 1.15;
}

.information-label {
    color: #7a4a00;
    font-weight: bold;
}

.information-value {
    min-width: 0;

    color: #a9854e;

    overflow-wrap: anywhere;
}


/* =========================
   完了メッセージ
========================= */
.completion-message {
    margin: 6px 0 0;

    color: #7a4a00;

    text-align: center;
    font-size: 12px;
    line-height: 1.2;
}

.continue-message {
    margin: 3px 0 5px;

    color: #a9854e;

    text-align: center;
    font-size: 12px;
    line-height: 1.2;
}


/* =========================
   ボタン
========================= */
.button-area {
    margin-bottom: 6px;

    text-align: center;
}

.button-area form {
    margin: 0;
}

.menu-button {
    min-width: 140px;

    padding: 5px 20px;

    border: none;
    border-radius: 5px;

    background-color: #783f04;
    color: #ffffff;

    cursor: pointer;

    font-size: 12px;
}

.menu-button:hover {
    background-color: #5d2f02;
}


/* =========================
   フッター
========================= */
.completion-page footer,
.completion-page .footer {
    width: 100%;
    min-height: 30px;

    margin: 0;
    padding: 7px 10px;

    background-color: #7a4a00;
    color: #ffffff;

    text-align: center;
    font-size: 12px;

    box-sizing: border-box;
}

.completion-page footer p,
.completion-page .footer p {
    margin: 0;

    line-height: 1.2;
}


/* =========================
   スマートフォン対応
========================= */
@media screen and (max-width: 700px) {

    .completion-header {
        background-size: auto 85px;
    }

    .completion-main {
        padding: 15px 20px 0;
    }

    .completion-content {
        flex-direction: column;

        width: 100%;
        max-width: 100%;

        gap: 10px;
    }

    .completion-image-area {
        flex-basis: auto;

        width: 100%;
    }

    .completion-image {
        width: 100%;
        height: auto;
        max-height: 230px;
    }

    .member-information {
        width: 100%;
        min-height: 0;
    }

    .information-row {
        grid-template-columns: 110px 1fr;
    }
}

</style>
</head>

<body>

<div class="completion-page">

    <!-- 上部ロゴ・テント部分 -->
    <header class="completion-header">

        <div class="brand-area">

            <span class="brand-name">
                Honey Bloom.
            </span>

            <img
                src="${pageContext.request.contextPath}/images/icon.png"
                class="brand-icon"
                alt="Honey Bloomのアイコン">

        </div>

        <p class="header-subtitle">
            － MEMBER INFORMATION －
        </p>

    </header>


    <!-- メイン部分 -->
    <main class="completion-main">

        <!-- 完了タイトル -->
        <h1 class="completion-title">
            会員情報を変更しました。
        </h1>

        <!-- タイトル下の飾り線 -->
        <img
            src="${pageContext.request.contextPath}/images/line1.png"
            class="title-line"
            alt="">


        <!-- 左側画像と右側会員情報 -->
        <div class="completion-content">

            <!-- 左側画像 -->
            <div class="completion-image-area">

                <img
                    src="${pageContext.request.contextPath}/images/edit_complete_top.png"
                    class="completion-image"
                    alt="会員情報変更完了">

            </div>


            <!-- 右側の会員情報 -->
            <section class="member-information">

                <h2 class="information-title">
                    変更後の会員情報
                </h2>


                <div class="information-row">

                    <span class="information-label">
                        会員ID
                    </span>

                    <span class="information-value">
                        <c:out value="${member.memberId}" />
                    </span>

                </div>


                <div class="information-row">

                    <span class="information-label">
                        氏名
                    </span>

                    <span class="information-value">
                        <c:out value="${member.memberName}" />
                    </span>

                </div>


                <div class="information-row">

                    <span class="information-label">
                        郵便番号
                    </span>

                    <span class="information-value">
                        <c:out value="${member.postalCode}" />
                    </span>

                </div>


                <div class="information-row">

                    <span class="information-label">
                        住所
                    </span>

                    <span class="information-value">
                        <c:out value="${member.address}" />
                    </span>

                </div>


                <div class="information-row">

                    <span class="information-label">
                        電話番号
                    </span>

                    <span class="information-value">
                        <c:out value="${member.phoneNumber}" />
                    </span>

                </div>


                <div class="information-row">

                    <span class="information-label">
                        生年月日
                    </span>

                    <span class="information-value">
                        <c:out value="${member.birthDate}" />
                    </span>

                </div>


                <div class="information-row">

                    <span class="information-label">
                        メールアドレス
                    </span>

                    <span class="information-value">
                        <c:out value="${member.email}" />
                    </span>

                </div>


                <div class="information-row">

                    <span class="information-label">
                        支払方法
                    </span>

                    <span class="information-value">

                        <c:choose>

                            <c:when
                                test="${member.paymentMethod == 'CREDIT'
                                    || member.paymentMethod == 'CREDIT_CARD'}">

                                クレジットカード

                            </c:when>

                            <c:when
                                test="${member.paymentMethod == 'BANK'}">

                                銀行振込

                            </c:when>

                            <c:when
                                test="${member.paymentMethod == 'COD'}">

                                代金引換

                            </c:when>

                            <c:otherwise>

                                <c:out
                                    value="${member.paymentMethod}" />

                            </c:otherwise>

                        </c:choose>

                    </span>

                </div>

            </section>

        </div>


        <!-- 更新完了メッセージ -->
        <p class="completion-message">
            ご登録内容を更新しました。
        </p>

        <p class="continue-message">
            引き続き、Honey Bloomでのお買い物をお楽しみください。
        </p>


        <!-- メニューへ戻るボタン -->
        <div class="button-area">

            <form
                action="${pageContext.request.contextPath}/main"
                method="get">

                <button
                    type="submit"
                    class="menu-button">

                    メニューへ戻る

                </button>

            </form>

        </div>

    </main>


    <!-- フッター -->
    <jsp:include page="common/footer.jsp" />

</div>

</body>
</html>