<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"  %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"  %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>在庫管理</title>
<style>
body {
    font-family: sans-serif;
    margin: 30px;
    background-color: #f7f7f7;
}
main {
    max-width: 1000px;
    margin: 0 auto;
    padding: 24px;
    background-color: white;
}
table {
    width: 100%;
    border-collapse: collapse;
}
th, td {
    border: 1px solid #cccccc;
    padding: 10px;
    text-align: left;
}
th {
    background-color: #eeeeee;
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
}
.message-error {
    color: #c62828;
}
input[type="number"] {
    width: 90px;
}
button {
    padding: 6px 12px;
}
</style>
</head>

<body>
<main>
    <h1>在庫管理</h1>
    
    <c:if test="${not empty successMessage}"> 
        <p class="message-success">
            <c:out value="${successMessage}"  />
        </p>
    </c:if>
    
    <c:if test="${not empty errorMessage}">
        <p class="message-error">
            <c:out value= "${errorMessage}" />
        </p>
     </c:if>
     
     <table>
         <thead>
             <tr>
                 <th>商品ID</th>
                 <th>商品名</th>
                 <th>価格</th>
                 <th>現在庫</th>
                 <th>在庫変更</th>
             </tr>
         </thead>
         
         <tbody>
             <c:forEach var="product" items="${productList}">
                  <tr>
                      <td><c:out value="${product.id}" /></td>
                      <td><c:out value="${product.name}" /></td>
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
                               <c:when test="${product.stock <= 5}">
                                   <span class="stock-low">
                                       <c:out value="${product.stock}" />
                                       個（残り僅か）
                                   </span>
                               </c:when>
                               <c:otherwise>
                                   <c:out value="${product.stock}" />個
                               </c:otherwise>
                           </c:choose>
                       </td>
                       <td>
                           <form method="post"
                               action="${pageContext.request.contextPath}/inventory/update">
                               <input type="hidden"
                                   name="productId"
                                   value="${product.id}">
                               <input type="number"
                                   name="stock"
                                   min="0"
                                   required
                                   value="${product.stock}">
                               <button type="submit">更新</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                
                <c:if test="${empty productList}">
                    <tr>
                        <td colspan="5">
                            商品が登録されていません。
                        </td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </main>
    </body>
    </html>