<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html lang="ja">

<head><link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/favicon.ico">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>会員管理</title>
</head>

<body>

	<main>

		<div>

			<h1>会員管理</h1>

			<form action="${pageContext.request.contextPath}/admin" method="get">

				<button type="submit">管理者画面へ戻る</button>

			</form>

		</div>

		<c:choose>

			<%-- 会員情報が存在しない場合 --%>
			<c:when test="${empty memberList}">

				<p>会員情報はありません。</p>

			</c:when>

			<%-- 会員情報が存在する場合 --%>
			<c:otherwise>

				<table border="1">

					<thead>

						<tr>
							<th>会員ID</th>
							<th>氏名</th>
							<th>メールアドレス</th>
							<th>現在の権限</th>
							<th>操作</th>
						</tr>

					</thead>

					<tbody>

						<c:forEach var="member" items="${memberList}">

							<tr>

								<td><c:out value="${member.memberId}" /></td>

								<td><c:out value="${member.memberName}" /></td>

								<td><c:out value="${member.email}" /></td>

								<td><c:out value="${member.role}" /></td>

								<td>
									<%-- 一般会員の場合 --%> <c:if test="${member.role != 'admin'}">

										<form
											action="${pageContext.request.contextPath}/admin/member/role"
											method="post" onsubmit="return confirm('この会員を管理者に変更しますか？');">

											<input type="hidden" name="memberId"
												value="${member.memberId}"> <input type="hidden"
												name="role" value="admin">

											<button type="submit">管理者にする</button>

										</form>

									</c:if> <%-- 管理者の場合 --%> <c:if test="${member.role == 'admin'}">

										<form
											action="${pageContext.request.contextPath}/admin/member/role"
											method="post" onsubmit="return confirm('この管理者を一般会員に戻しますか？');">

											<input type="hidden" name="memberId"
												value="${member.memberId}"> <input type="hidden"
												name="role" value="user">

											<button type="submit">一般会員に戻す</button>

										</form>

									</c:if>

								</td>

							</tr>

						</c:forEach>

					</tbody>

				</table>

			</c:otherwise>

		</c:choose>

	</main>

</body>
</html>