<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/WEB-INF/tld/habraclone.tld" prefix="habraclone" %>

<fmt:setLocale value="${pageContext.response.locale}" scope="session" />

<html>

<head>
    <title><fmt:message key="users.title" /></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/resources/images/favicon.png" sizes="32x32" />
    <script type="text/javascript"src="${pageContext.request.contextPath}/resources/js/validation.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-1.11.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.leanModal.min.js"></script>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/users.css" />
</head>

<body>

<div class="container">
    <%--<div class="content">--%>

        <jsp:include page="header.jsp"/>


        <div class="page-header">
            <h1 class="page-header__title page-header__title_flow"><fmt:message key="users.title" /></h1>
        </div>

        <div class="peoples_list">
            <div class="users_header">
                <div class="rating"><fmt:message key="users.label.rating" /></div>
                <div class="karma">карма</div>
            </div>

            <div id="peoples" class="users peoples">
                <c:if test="${not empty requestScope.USERS}">
                    <jsp:useBean id="currentDate" class="java.util.Date" />
                    <c:forEach items="${requestScope.USERS}" var="user" varStatus="var">
                        <div class="user">
                            <c:if test="${var.count eq 1}">
                                <div class="lion_king"></div>
                            </c:if>
                            <div class="rating"><c:out value="${user.rating}" /></div>
                            <div class="karma"><c:out value="${user.rating / 2}" /></div>

                            <div class="avatar">
                                <a href="${pageContext.request.contextPath}/user/${user.userName}">
                                    <img src="${pageContext.request.contextPath}/resources/images/user.png">
                                </a>
                            </div>

                            <div class="info">
                                <div class="userlogin">
                                    <div class="username">
                                        <a href="${pageContext.request.contextPath}/user/${user.userName}"><c:out value="${user.userName}" /></a>
                                    </div>
                                </div>
                                <div class="lifetime">
                                    <c:set var="regDateTime" value="${fn:replace(user.registrationDate, 'T', ' ')}" />
                                    <fmt:parseDate value="${regDateTime}" pattern="yyyy-MM-dd HH:mm" var="regDate" type="both" />
                                    <!-- 1 * 24 * 60 * 60 * 1000 = 86400000 -->
                                    <fmt:formatNumber maxFractionDigits="0" value="${(currentDate.time - regDate.time) / 86400000}" />
                                    <fmt:message key="users.label.experience_in_days" />
                                </div>
                                <c:if test="${fn:length(user.articles) ne 0}">
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

    <%--</div>--%>
</div>
</body>

</html>
