<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>商品編集</title>
<style>
body {
    font-family: sans-serif;
    margin: 30px;
    background-color: #f7f7f7;
}
main {
    max-width: 850px;
    margin: 0 auto;
    padding: 24px;
    background-color: white;
}
.form-grid {
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: 16px;
}
.form-field {
    display: flex;
    flex-direction: column;
    gap: 6px;
}
.form-field-wide {
    grid-column: 1 / -1;
}
input, select, textarea, button {
    font: inherit;
}
input, select, textarea {
    padding: 8px;
    box-sizing: border-box;
}
textarea {
    min-height: 130px;
    resize: vertical;
}
.preview {
    width: 260px;
    height: 190px;
    object-fit: cover;
    border: 1px solid #cccccc;
    border-radius: 8px;
}
.message-error {
    color: #c62828;
    font-weight: bold;
}
.actions {
    display: flex;
    gap: 12px;
    margin-top: 20px;
}
button, .back-link {
    display: inline-block;
    padding: 8px 14px;
    border: 1px solid #888888;
    border-radius: 3px;
    background-color: white;
    color: black;
    text-decoration: none;
    cursor: pointer;
}
.status {
    margin-bottom: 18px;
    font-weight: bold;
}
.status-active {
    color: #1b5e20;
}
.status-inactive {
    color: #666666;
}
@media (max-width: 700px) {
    .form-grid {
        grid-template-columns: 1fr;
    }
    .form-field-wide {
        grid-column: auto;
    }
}
</style>
</head>

<body>
<main>
    <h1>商品編集</h1>

    <c:if test="${not empty errorMessage}">
        <p class="message-error">
            <c:out value="${errorMessage}" />
        </p>
    </c:if>

    <p>商品ID：<c:out value="${product.productId}" /></p>

    <p class="status">
        状態：
        <c:choose>
            <c:when test="${product.active}">
                <span class="status-active">取扱中</span>
            </c:when>
            <c:otherwise>
                <span class="status-inactive">取扱停止</span>
            </c:otherwise>
        </c:choose>
    </p>

    <form method="post"
        action="${pageContext.request.contextPath}/inventory/edit">

        <input type="hidden"
            name="productId"
            value="${product.productId}">

        <div class="form-grid">
            <div class="form-field">
                <label for="productName">商品名</label>
                <input id="productName"
                    type="text"
                    name="productName"
                    maxlength="100"
                    required
                    value="${product.productName}">
            </div>

            <div class="form-field">
                <label for="price">価格</label>
                <input id="price"
                    type="number"
                    name="price"
                    min="1"
                    step="1"
                    required
                    value="${product.price}">
            </div>

            <div class="form-field">
                <label for="stock">在庫数</label>
                <input id="stock"
                    type="number"
                    name="stock"
                    min="0"
                    step="1"
                    required
                    value="${product.stock}">
            </div>

            <div class="form-field">
                <label for="categoryId">カテゴリー</label>
                <select id="categoryId" name="categoryId" required>
                    <option value="1"
                        ${product.categoryId == 1 ? 'selected' : ''}>
                        CAKES
                    </option>
                    <option value="2"
                        ${product.categoryId == 2 ? 'selected' : ''}>
                        BAKES
                    </option>
                    <option value="3"
                        ${product.categoryId == 3 ? 'selected' : ''}>
                        PASTRIES
                    </option>
                </select>
            </div>

            <div class="form-field">
                <label for="imageUrl">商品画像</label>
                <select id="imageUrl"
                    name="imageUrl"
                    required
                    onchange="changePreview(this)">
                    <c:forEach var="image" items="${imageOptions}">
                        <option value="${image.key}"
                            ${image.key == product.imageUrl ? 'selected' : ''}>
                            <c:out value="${image.value}" />
                            （<c:out value="${image.key}" />）
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-field">
                <span>画像プレビュー</span>
                <img id="imagePreview"
                    class="preview"
                    src="${pageContext.request.contextPath}${product.imageUrl}"
                    alt="商品画像プレビュー">
            </div>

            <div class="form-field form-field-wide">
                <label for="description">商品説明（任意・1000文字以内）</label>
                <textarea id="description"
                    name="description"
                    maxlength="1000"><c:out value="${product.description}" /></textarea>
            </div>
        </div>

        <div class="actions">
            <button type="submit">変更を保存</button>
            <a class="back-link"
                href="${pageContext.request.contextPath}/inventory">
                一覧へ戻る
            </a>
        </div>
    </form>
</main>

<script>
function changePreview(selectElement) {
    const contextPath = '${pageContext.request.contextPath}';
    document.getElementById('imagePreview').src =
        contextPath + selectElement.value;
}
</script>
</body>
</html>