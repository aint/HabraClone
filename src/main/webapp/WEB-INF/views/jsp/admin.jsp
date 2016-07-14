<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>

<head>
    <title>Admin</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/resources/images/favicon.png" sizes="32x32" />
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css" />
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/index_main.css" />
</head>

<body>

<div class="container">

    <sec:authorize access="!hasAnyRole('ADMIN')">
    <div class="div_centred" style="padding-top: 10%">
        <c:if test="${not empty error_msg}" >
            <div class="error">
                    ${error_msg}
            </div>
        </c:if>
        <form action="${pageContext.request.contextPath}/login" method="post">
            <div style="display: inline-block;">
                username<br>
                <input type="text" name="username">
                <br>password<br>
                <input type="password" name="password">
                <br>
                <input type="submit" value="Login">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            </div>
        </form>

    </div>
    </sec:authorize>
    <sec:authorize access="hasAnyRole('ADMIN')">
        <div class="div_centred" style="padding-top: 10%">
        <form action="${pageContext.request.contextPath}/logout" method="post">
            <input type="submit" value="Logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        </form>
    </div>
    </sec:authorize>

</body>
</html>
