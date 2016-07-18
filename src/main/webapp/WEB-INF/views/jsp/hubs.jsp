<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>

<head>
    <title><fmt:message key="hubs.title" /></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/resources/images/favicon.png" sizes="32x32" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/validation.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-1.11.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.leanModal.min.js"></script>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css" />
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/index_main.css" />
</head>

<body>

<div class="container">

    <jsp:include page="header.jsp"/>

    <div class="page-header div_centred">
        <h1 class="page-header__title page-header__title_flow"><fmt:message key="hubs.title" /></h1>
    </div>

    <div class="hubs_list">
        <c:forEach items="${HUBS}" var="hub">
            <div class="hub">
                <div class="habraindex">${hub.rating}</div>
                <div class="info">
                    <div class="title">
                        <a href="${pageContext.request.contextPath}/hubs/${hub.id}/articles/">${hub.name}</a>
                    </div>
                    <div class="stat">
                        <a href="${pageContext.request.contextPath}/hubs/${hub.id}/subscribers/" class="members_count">
                            241k <fmt:message key="hubs.label.subscribers" />,
                        </a>
                        <a href="${pageContext.request.contextPath}/hubs/${hub.id}/articles/">
                            ${fn:length(hub.articles)} <fmt:message key="hubs.label.publications" />,
                        </a>
                        <fmt:parseDate value="${fn:replace(hub.creationDate, 'T', ' ')}" pattern="yyyy-MM-dd HH:mm" var="creationDate" type="both" />
                        <fmt:message key="hubs.label.creation_date" />
                        <fmt:formatDate pattern="dd MMMM yyyy HH:mm" value="${creationDate}" />
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>

</div>
</body>
</html>
