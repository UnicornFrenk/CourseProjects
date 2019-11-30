<%--
  Created by IntelliJ IDEA.
  User: tanya_melgui
  Date: 13.10.19
  Time: 19:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Create Item</title>
</head>
<body>
<h2>Creating new item</h2>
<c:set var="item"/>
<form method="post" action="${pageContext.request.contextPath}/createitem">
    <table border="2">
        <tr>

            <td>Name</td>
            <td>Description</td>
            <td>Total Quantity</td>
            <td>Category</td>
            <td>Price For One</td>


        </tr>
        <tr>
            <td><input type="text" name="name"></td>
            <td><input type="text" name="description"></td>
            <td><input type="text" name="quantity">
            </td>
            <td><select name="categoryId">
                <c:forEach items="${categories}" var="cat">
                    <option value="${cat.idCategory}">${cat.nameCategory}</option>
                </c:forEach>
            </select></td>
            <td><input type="text" name="price" >
            </td>
        </tr>
    </table>

    <button type="submit" name="create">create</button>
</form>
</form>
</body>
</html>
