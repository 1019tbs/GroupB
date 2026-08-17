<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="ja">
<head><link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/favicon.ico">
<meta charset="UTF-8">
<title>商品・在庫管理</title>
<style>
body {
    font-family: sans-serif;
    margin: 30px;
    background-color: #f7f7f7;
}
main {
    max-width: 1250px;
    margin: 0 auto;
    padding: 24px;
    background-color: white;
}
h1, h2 {
    margin-top: 0;
}
.register-panel {
    margin-bottom: 28px;
    padding: 18px;
    border: 1px solid #cccccc;
    background-color: #fafafa;
}
.form-grid {
    display: grid;
    grid-template-columns: repeat(3, minmax(180px, 1fr));
    gap: 14px;
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
    padding: 7px;
    box-sizing: border-box;
}
textarea {
    min-height: 85px;
    resize: vertical;
}
.preview {
    width: 150px;
    height: 110px;
    object-fit: cover;
    border: 1px solid #cccccc;
    border-radius: 8px;
    background-color: white;
}
table {
    width: 100%;
    border-collapse: collapse;
}
th, td {
    border: 1px solid #cccccc;
    padding: 9px;
    text-align: left;
    vertical-align: middle;
}
th {
    background-color: #eeeeee;
}
.product-image {
    width: 90px;
    height: 70px;
    object-fit: cover;
    border-radius: 6px;
}
.description {
    max-width: 260px;
    white-space: pre-wrap;
    word-break: break-word;
}
.stock-zero {
    color: #c62828;
    font-weight: bold;
}
.stock-low {
    color: #ef6c00;
    font-weight: bold;
}
.message-success {
    color: #1b5e20;
    font-weight: bold;
}
.message-error {
    color: #c62828;
    font-weight: bold;
}
.status-active {
    color: #1b5e20;
    font-weight: bold;
}
.status-inactive {
    color: #666666;
    font-weight: bold;
}
.inactive-row {
    background-color: #f1f1f1;
    color: #666666;
}
.actions {
    display: flex;
    flex-direction: column;
    gap: 7px;
    min-width: 105px;
}
.actions form {
    margin: 0;
}
.actions button,
.edit-link,
.register-button {
    display: inline-block;
    padding: 7px 11px;
    border: 1px solid #888888;
    border-radius: 3px;
    background-color: #ffffff;
    color: #111111;
    text-decoration: none;
    cursor: pointer;
}
.stop-button {
    color: #a00000;
}
.resume-button {
    color: #1b5e20;
}
.stock-form {
    display: flex;
    gap: 5px;
    align-items: center;
}
.stock-form input[type="number"] {
    width: 80px;
}
.note {
    color: #555555;
    font-size: 0.9rem;
}
.page-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    gap: 16px;
    margin-bottom: 20px;
}
.page-header h1 {
    margin: 0;
}
.back-form {
    margin: 0;
}
.back-button {
    padding: 8px 14px;
    border: 1px solid #888888;
    border-radius: 4px;
    background-color: #ffffff;
    color: #111111;
    cursor: pointer;
}
.back-button:hover {
    background-color: #eeeeee;
}
@media (max-width: 900px) {
    .form-grid {
        grid-template-columns: 1fr;
    }
    .form-field-wide {
        grid-column: auto;
    }
    table {
        display: block;
        overflow-x: auto;
    }
}
</style>
</head>

<body>
<main>
    <div class="page-header">
        <h1>商品・在庫管理</h1>

        <form class="back-form"
            action="${pageContext.request.contextPath}/admin"
            method="get">
            <button class="back-button" type="submit">
                管理者画面へ戻る
            </button>
        </form>
    </div>

    <c:if test="${not empty successMessage}">
        <p class="message-success">
            <c:out value="${successMessage}" />
        </p>
    </c:if>

    <c:if test="${not empty errorMessage}">
        <p class="message-error">
            <c:out value="${errorMessage}" />
        </p>
    </c:if>

    <section class="register-panel">
        <h2>新規商品登録</h2>

        <form method="post"
            action="${pageContext.request.contextPath}/inventory/register">

            <div class="form-grid">
                <div class="form-field">
                    <label for="productName">商品名</label>
                    <input id="productName"
                        type="text"
                        name="productName"
                        maxlength="100"
                        required>
                </div>

                <div class="form-field">
                    <label for="price">価格</label>
                    <input id="price"
                        type="number"
                        name="price"
                        min="1"
                        step="1"
                        required>
                </div>

                <div class="form-field">
                    <label for="stock">在庫数</label>
                    <input id="stock"
                        type="number"
                        name="stock"
                        min="0"
                        step="1"
                        required>
                </div>

                <div class="form-field">
                    <label for="categoryId">カテゴリー</label>
                    <select id="categoryId" name="categoryId" required>
                        <option value="">選択してください</option>
                        <option value="1">CAKES</option>
                        <option value="2">BAKES</option>
                        <option value="3">PASTRIES</option>
                    </select>
                </div>

                <div class="form-field form-field-wide">
                    <span>販売方法（1つ以上選択）</span>
                    <label>
                        <input type="checkbox"
                            name="pickupAvailable"
                            value="true"
                            checked>
                        店頭受取可能
                    </label>
                    <label>
                        <input type="checkbox"
                            name="deliveryAvailable"
                            value="true"
                            checked>
                        通販可能
                    </label>
                </div>

                <div class="form-field">
                    <label for="imageUrl">商品画像</label>
                    <select id="imageUrl"
                        name="imageUrl"
                        required
                        onchange="changePreview(this, 'registerPreview')">
                        <option value="">選択してください</option>
                        <c:forEach var="image" items="${imageOptions}">
                            <option value="${image.key}">
                                <c:out value="${image.value}" />
                                （<c:out value="${image.key}" />）
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-field">
                    <span>画像プレビュー</span>
                    <img id="registerPreview"
                        class="preview"
                        alt="選択した商品画像のプレビュー">
                </div>

                <div class="form-field form-field-wide">
                    <label for="description">商品説明（任意・1000文字以内）</label>
                    <textarea id="description"
                        name="description"
                        maxlength="1000"></textarea>
                </div>
            </div>

            <p class="note">
                同じ商品名は重複登録できません。取扱停止中の商品は下の一覧から再開してください。
            </p>

            <button class="register-button" type="submit">
                商品登録
            </button>
        </form>
    </section>

    <section>
        <h2>登録済み商品</h2>

        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>画像</th>
                    <th>商品情報</th>
                    <th>価格</th>
                    <th>現在庫</th>
                    <th>在庫変更</th>
                    <th>状態</th>
                    <th>販売方法</th>
                    <th>操作</th>
                </tr>
            </thead>

            <tbody>
                <c:forEach var="product" items="${productList}">
                    <tr class="${product.active ? '' : 'inactive-row'}">
                        <td>
                            <c:out value="${product.productId}" />
                        </td>

                        <td>
                            <img class="product-image"
                                src="${pageContext.request.contextPath}${product.imageUrl}"
                                alt="${product.productName}">
                        </td>

                        <td>
                            <strong>
                                <c:out value="${product.productName}" />
                            </strong>
                            <div>
                                <c:choose>
                                    <c:when test="${product.categoryId == 1}">CAKES</c:when>
                                    <c:when test="${product.categoryId == 2}">BAKES</c:when>
                                    <c:when test="${product.categoryId == 3}">PASTRIES</c:when>
                                    <c:otherwise>未設定</c:otherwise>
                                </c:choose>
                            </div>
                            <div class="description">
                                <c:out value="${product.description}" />
                            </div>
                        </td>

                        <td>
                            <fmt:formatNumber
                                value="${product.price}"
                                pattern="#,##0" />円
                        </td>

                        <td>
                            <c:choose>
                                <c:when test="${product.stock == 0}">
                                    <span class="stock-zero">在庫切れ</span>
                                </c:when>
                                <c:when test="${product.stock le 5}">
                                    <span class="stock-low">
                                        <c:out value="${product.stock}" />個
                                        （残りわずか）
                                    </span>
                                </c:when>
                                <c:otherwise>
                                    <c:out value="${product.stock}" />個
                                </c:otherwise>
                            </c:choose>
                        </td>

                        <td>
                            <c:if test="${product.pickupAvailable}">
                                <div>店頭受取</div>
                            </c:if>
                            <c:if test="${product.deliveryAvailable}">
                                <div>通販</div>
                            </c:if>
                        </td>

                        <td>
                            <form class="stock-form"
                                method="post"
                                action="${pageContext.request.contextPath}/inventory/update">
                                <input type="hidden"
                                    name="productId"
                                    value="${product.productId}">
                                <input type="number"
                                    name="stock"
                                    min="0"
                                    step="1"
                                    required
                                    value="${product.stock}">
                                <button type="submit">更新</button>
                            </form>
                        </td>

                        <td>
                            <c:choose>
                                <c:when test="${product.active}">
                                    <span class="status-active">取扱中</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="status-inactive">取扱停止</span>
                                </c:otherwise>
                            </c:choose>
                        </td>

                        <td>
                            <div class="actions">
                                <a class="edit-link"
                                    href="${pageContext.request.contextPath}/inventory/edit?productId=${product.productId}">
                                    編集
                                </a>

                                <c:choose>
                                    <c:when test="${product.active}">
                                        <form method="post"
                                            action="${pageContext.request.contextPath}/inventory/stop">
                                            <input type="hidden"
                                                name="productId"
                                                value="${product.productId}">
                                            <button class="stop-button"
                                                type="submit"
                                                onclick="return confirm('この商品の取扱いを停止しますか？在庫数は保持されます。');">
                                                取扱停止
                                            </button>
                                        </form>
                                    </c:when>

                                    <c:otherwise>
                                        <form method="post"
                                            action="${pageContext.request.contextPath}/inventory/resume">
                                            <input type="hidden"
                                                name="productId"
                                                value="${product.productId}">
                                            <button class="resume-button"
                                                type="submit"
                                                onclick="return confirm('この商品の取扱いを再開しますか？');">
                                                取扱再開
                                            </button>
                                        </form>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </td>
                    </tr>
                </c:forEach>

                <c:if test="${empty productList}">
                    <tr>
                        <td colspan="9">商品が登録されていません。</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </section>
</main>

<script>
function changePreview(selectElement, imageId) {
    const image = document.getElementById(imageId);
    const selectedPath = selectElement.value;
    const contextPath = '${pageContext.request.contextPath}';

    if (selectedPath) {
        image.src = contextPath + selectedPath;
        image.style.display = 'block';
    } else {
        image.removeAttribute('src');
        image.style.display = 'none';
    }
}

document.getElementById('registerPreview').style.display = 'none';
</script>
</body>
</html>
