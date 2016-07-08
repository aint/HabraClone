<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE HTML>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/resources/images/favicon.png" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/errorPage.css" />
    <title>HTTP Status 404</title>
</head>

<body>
    <div class="centerBlock">
        <div class="head">
            <b>HTTP Status 404</b>
        </div>
        <div class="body">
            Page not found<br>
            <c:out value="${requestScope['javax.servlet.error.message']}" />
        </div>
    </div>
</body>

</html>
