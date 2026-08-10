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
    height: 120px;

    text-align: center;

    background-image:
        url("${pageContext.request.contextPath}/images/edit_top.png");

    background-repeat: no-repeat;
    background-position: center top;
    background-size: 100% 120px;

    box-sizing: border-box;
}

.brand-area {
    display: flex;
    justify-content: center;
    align-items: center;

    padding-top: 13px;
}

.brand-name {
    color: #783f04;

    font-family:
        Georgia,
        "Times New Roman",
        serif;

    font-size: clamp(32px, 3.2vw, 44px);
    font-weight: bold;
    letter-spacing: 1px;
}

.brand-icon {
    width: 54px;
    height: 54px;

    margin-left: 4px;

    object-fit: contain;
}

.header-subtitle {
    margin: -5px 0 0;

    color: #a9854e;

    font-family:
        Georgia,
        "Times New Roman",
        serif;

    font-size: 16px;
}


/* =========================
   メイン部分
========================= */
.completion-main {
    flex: 1;

    width: 100%;
    padding: 34px 24px 48px;

    box-sizing: border-box;
}

.completion-title {
    margin: 0;

    color: #783f04;

    text-align: center;
    font-size: clamp(26px, 2.5vw, 34px);
    line-height: 1.4;
}

.title-line {
    display: block;

    width: 420px;
    max-width: 80%;
    height: 12px;

    margin: 4px auto 24px;

    object-fit: contain;
}


/* =========================
   画像と会員情報
========================= */
.completion-content {
    display: grid;
    grid-template-columns: minmax(0, 1.15fr) minmax(340px, 0.85fr);
    align-items: stretch;

    gap: 24px;

    width: min(100%, 1000px);

    margin: 0 auto;
}

.completion-image-area {
    width: 100%;
    min-width: 0;
}

.completion-image {
    display: block;

    width: 100%;
    height: 100%;
    min-height: 310px;

    border-radius: 16px;

    object-fit: cover;
}


/* =========================
   会員情報の枠
========================= */
.member-information {
    width: 100%;
    min-height: 310px;

    padding: 22px 26px;

    background-color: #fff0e3;

    border:
        3px solid
        rgba(253, 142, 188, 0.70);

    border-radius: 18px;

    box-sizing: border-box;
}

.information-title {
    margin: 0 0 14px;

    color: #783f04;

    text-align: center;
    font-size: 20px;
    font-weight: bold;
    line-height: 1.4;
}

.information-row {
    display: grid;

    grid-template-columns: 120px 1fr;
    column-gap: 12px;

    margin-bottom: 5px;

    font-size: 15px;
    line-height: 1.35;
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
    margin: 24px 0 0;

    color: #7a4a00;

    text-align: center;
    font-size: 17px;
    line-height: 1.5;
}

.continue-message {
    margin: 4px 0 18px;

    color: #a9854e;

    text-align: center;
    font-size: 15px;
    line-height: 1.5;
}


/* =========================
   ボタン
========================= */
.button-area {
    margin-bottom: 0;

    text-align: center;
}

.button-area form {
    margin: 0;
}

.menu-button {
    min-width: 220px;

    padding: 14px 28px;

    border: none;
    border-radius: 8px;

    background-color: #783f04;
    color: #ffffff;

    cursor: pointer;

    font-size: 16px;
    font-weight: bold;

    transition: background-color 0.2s, transform 0.2s;
}

.menu-button:hover {
    background-color: #5d2f02;
    transform: translateY(-1px);
}

.menu-button:focus-visible {
    outline: 3px solid rgba(253, 142, 188, 0.75);
    outline-offset: 3px;
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
@media screen and (max-width: 900px) {

    .completion-content {
        grid-template-columns: 1fr;

        width: min(100%, 620px);
    }

    .completion-image {
        height: clamp(230px, 48vw, 340px);
        min-height: 0;
    }

    .member-information {
        min-height: 0;
    }
}

@media screen and (max-width: 700px) {

    .completion-header {
        height: 92px;
        background-size: auto 92px;
    }

    .brand-area {
        padding-top: 8px;
    }

    .brand-name {
        font-size: 30px;
    }

    .brand-icon {
        width: 43px;
        height: 43px;
    }

    .header-subtitle {
        font-size: 13px;
    }

    .completion-main {
        padding: 26px 16px 36px;
    }

    .completion-content {
        width: 100%;
        max-width: 100%;
        gap: 16px;
    }

    .completion-image {
        height: 52vw;
        min-height: 210px;
        max-height: 300px;
    }

    .member-information {
        padding: 20px;
    }

    .information-row {
        grid-template-columns: 105px 1fr;

        font-size: 14px;
    }

    .menu-button {
        width: 100%;
        max-width: 320px;
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
