<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/WEB-INF/tld/habraclone.tld" prefix="habraclone" %>

<html>

<head>
    <title><fmt:message key="users.title" /></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/resources/images/favicon.png" sizes="32x32" />
    <script type="text/javascript"src="${pageContext.request.contextPath}/resources/js/validation.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-1.11.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.leanModal.min.js"></script>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css" />
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/index_main.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/users.css" />
</head>

<body>

<div class="container">

    <jsp:include page="includes/header.jsp"/>


    <div class="page-header div_centred">
        <h1 class="page-header__title page-header__title_flow"><fmt:message key="users.title" /></h1>
    </div>

    <div class="peoples_list">
        <div class="users_header">
            <div class="rating"><fmt:message key="user_profile.label.rating" /></div>
            <div class="karma"><fmt:message key="user_profile.label.karma" /></div>
        </div>

        <div id="peoples" class="users peoples">
            <c:if test="${not empty requestScope.USERS}">
                <jsp:useBean id="currentDate" class="java.util.Date" />
                <c:forEach items="${requestScope.USERS}" var="user" varStatus="var">
                    <div class="user">
                        <c:if test="${var.count eq 1}">
                            <div class="lion_king"></div>
                        </c:if>
                        <div class="rating">${user.rating}</div>
                        <div class="karma">${user.rating / 2}</div>

                        <div class="avatar">
                            <a href="${pageContext.request.contextPath}/users/${user.username}">
                                <img src="${pageContext.request.contextPath}/resources/images/user.png">
                            </a>
                        </div>

                        <div class="info">
                            <div class="userlogin">
                                <div class="username">
                                    <a href="${pageContext.request.contextPath}/users/${user.username}">${user.username}</a>
                                </div>
                            </div>
                            <div class="lifetime">
                                <fmt:parseDate value="${fn:replace(user.registrationDate, 'T', ' ')}" pattern="yyyy-MM-dd HH:mm" var="regDate" type="both" />
                                <!-- 1 * 24 * 60 * 60 * 1000 = 86400000 -->
                                <fmt:formatNumber maxFractionDigits="0" value="${(currentDate.time - regDate.time) / 86400000}" />
                                <fmt:message key="users.label.experience_in_days" />
                            </div>
                            <c:if test="${user.articlesCount ne 0}">
                                <div class="last_post">
                                    <fmt:message key="users.label.latest_article" />
                                    <habraclone:linkToLatestArticle user="${user}" />
                                </div>
                            </c:if>
                        </div>

                        <div class="clear"></div>
                    </div>
                </c:forEach>
            </c:if>
        </div>

    </div>

</div>
</body>

</html>
