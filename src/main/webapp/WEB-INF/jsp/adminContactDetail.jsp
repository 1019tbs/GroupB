<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>

<!DOCTYPE html>
<html lang="ja">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>お問い合わせ詳細</title>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/adminContactDetail.css">

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/adminContactDetail.css">
</head>

<body>

	<main class="contact-detail-admin">

		<div class="page-header">

			<div class="page-heading">

				<h1>
					<i class="bi bi-chat-left-text"></i> お問い合わせ詳細
				</h1>

				<span class="page-subtitle"> Honey Bloom Admin </span>

			</div>

			<form action="${pageContext.request.contextPath}/admin/contact"
				method="get">

				<button type="submit" class="back-button">

					<i class="bi bi-arrow-left"></i> 一覧へ戻る

				</button>

			</form>

		</div>

		<div class="contact-detail">

			<div class="detail-row">

				<span class="detail-label"> お問い合わせ番号 </span> <span
					class="detail-value"> No.${contact.contactId} </span>

			</div>

			<div class="detail-row">

				<span class="detail-label"> 受付日時 </span> <span class="detail-value">
					${contact.createdAtFormat}
				</span>

			</div>

			<div class="detail-row">

				<span class="detail-label"> 対応状況 </span> <span class="detail-value">

					<c:choose>

						<c:when test="${contact.status == 1}">
                            対応済み
                        </c:when>

						<c:otherwise>
                            未対応
                        </c:otherwise>

					</c:choose>

				</span>

			</div>

			<div class="detail-row">

				<span class="detail-label"> お名前 </span> <span class="detail-value">

					<c:out value="${contact.customerName}" />

				</span>

			</div>

			<div class="detail-row">

				<span class="detail-label"> メールアドレス </span> <span
					class="detail-value"> <c:out value="${contact.email}" />

				</span>

			</div>

			<div class="detail-row">

				<span class="detail-label"> 電話番号 </span> <span class="detail-value">

					<c:out value="${contact.phone}" />

				</span>

			</div>

			<div class="detail-row">

				<span class="detail-label"> 件名 </span> <span class="detail-value">

					<c:out value="${contact.subject}" />

				</span>

			</div>

			<div class="detail-message">

				<span class="detail-label"> お問い合わせ内容 </span>

				<p class="message-text">

					<c:out value="${contact.message}" />

				</p>

			</div>

		</div>

	</main>

</body>
</html>