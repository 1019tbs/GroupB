<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html lang="ja">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>お問い合わせ管理</title>

<link rel="stylesheet"
    href="${pageContext.request.contextPath}/css/adminContact.css">

<!-- 管理画面で使用するアイコン -->
<link rel="stylesheet"
    href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
</head>

<body>

    <main class="contact-admin">

        <!-- ページ上部 -->
        <div class="page-header">

            <div class="page-heading">

                <h1>
                    <i class="bi bi-chat-left-text"></i>
                    お問い合わせ管理
                </h1>

                <span class="page-subtitle">
                    Honey Bloom Admin
                </span>

            </div>

            <form action="${pageContext.request.contextPath}/admin"
                method="get">

                <button type="submit" class="back-button">

                    <i class="bi bi-arrow-left"></i>

                    管理者画面へ戻る

                </button>

            </form>

        </div>

        <c:choose>

            <%-- お問い合わせが存在しない場合 --%>
            <c:when test="${empty contactList}">

                <div class="empty-message">

                    <i class="bi bi-inbox empty-icon"></i>

                    <p>お問い合わせ情報はありません。</p>

                </div>

            </c:when>

            <%-- お問い合わせが存在する場合 --%>
            <c:otherwise>

                <div class="contact-list">

                    <c:forEach var="contact" items="${contactList}">

                        <article class="contact-item">

                            <%-- 左側：日付情報 --%>
                            <div class="contact-date">

                                <span class="date-label">
                                    受付日
                                </span>

                                <span class="date-value">
                                    ${contact.createdAt}
                                </span>

                            </div>

                            <%-- 中央：お問い合わせ内容 --%>
                            <div class="contact-content">

                                <div class="contact-title">

                                    <span class="contact-id">
                                        No.${contact.contactId}
                                    </span>

                                    <h2>
                                        <c:out value="${contact.subject}" />
                                    </h2>

                                </div>

                                <div class="customer-info">

                                    <span class="customer-info-item">

                                        <i class="bi bi-person"></i>

                                        <c:out
                                            value="${contact.customerName}" />

                                    </span>

                                    <span class="customer-info-item">

                                        <i class="bi bi-envelope"></i>

                                        <c:out value="${contact.email}" />

                                    </span>

                                    <span class="customer-info-item">

                                        <i class="bi bi-telephone"></i>

                                        <c:out value="${contact.phone}" />

                                    </span>

                                </div>

                                <div class="contact-message">

                                    <p>
                                        <c:out value="${contact.message}" />
                                    </p>

                                </div>

                            </div>

                            <%-- 右側：操作ボタン --%>
                            <div class="contact-actions">

                                <form
                                    action="${pageContext.request.contextPath}/admin/contact/detail"
                                    method="get">

                                    <input type="hidden"
                                        name="contactId"
                                        value="${contact.contactId}">

                                    <button type="submit"
                                        class="action-button detail-button">

                                        詳細を見る

                                    </button>

                                </form>

                                <form
                                    action="${pageContext.request.contextPath}/admin/contact/status"
                                    method="post">

                                    <input type="hidden"
                                        name="contactId"
                                        value="${contact.contactId}">

                                    <button type="submit"
                                        class="action-button complete-button">

                                        対応済みにする

                                    </button>

                                </form>

                                <form
                                    action="${pageContext.request.contextPath}/admin/contact/delete"
                                    method="post"
                                    onsubmit="return confirm('このお問い合わせを削除しますか？');">

                                    <input type="hidden"
                                        name="contactId"
                                        value="${contact.contactId}">

                                    <button type="submit"
                                        class="action-button delete-button">

                                        <i class="bi bi-trash"></i>

                                        削除

                                    </button>

                                </form>

                            </div>

                        </article>

                    </c:forEach>

                </div>

            </c:otherwise>

        </c:choose>

    </main>

</body>
</html>