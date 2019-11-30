<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="translations" var="msg"/>
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="login" bundle="${msg}"/></title>
</head>
<body>
<%@include file="languages.jsp" %>
<c:forEach var="user" items="user"/>
<div class="form">
    <h1>Hi! We glad to see you!</h1><br>
    <h3>You can buy something only after registration!</h3>

    <table>
        <tr>
            <td>
                <form action="${pageContext.request.contextPath}/login">
                    <button type="submit" value="Войти"><fmt:message
                            key="log.in" bundle="${msg}"/></button>
                </form>
            </td>
            <td>
                <form action="${pageContext.request.contextPath}/registration">
                    <button type="submit" value="Регистрация"><fmt:message
                            key="registration" bundle="${msg}"/></button>
                </form>
            </td>
            <td>
                <form align="right" method="get"
                      action="${pageContext.request.contextPath}/createOrder">
                    <button><a
                            href="${pageContext.request.contextPath}/createOrder"><fmt:message
                            key="create.new.order" bundle="${msg}"/></a>
                    </button>
                </form>
            </td>
        </tr>
    </table>
    <br>
    <br>
    <br>

    <a href="${pageContext.request.contextPath}/itemlist">

        <fmt:message key="items" bundle="${msg}"/></a>
    <a href="${pageContext.request.contextPath}/users"><fmt:message
            key="users" bundle="${msg}"/></a>

    <h2>${locale}</h2>

</div>
</body>
</html>