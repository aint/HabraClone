<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>

<head>
    <title>${USER.username} <fmt:message key="user_profile.label.articles" /></title>
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

    <jsp:include page="includes/header.jsp"/>

    <jsp:include page="includes/profileHeader.jsp"/>

    <div class="column-wrapper">
        <div class="content_left">
            <jsp:include page="includes/userTabs.jsp" />
        </div>

        <div class="posts_list">
            <c:set var="articles" value="${(empty FAVORITES) ? USER.articles : USER.favorites}" />
            <c:choose>
                <c:when test="${not empty articles}">
                    <div class="posts shortcuts_items">
                        <c:forEach items="${articles}" var="article" varStatus="var">
                            <div class="post post_teaser shortcuts_item">

                                <div class="post__header">
                                    <span class="post__time_published">${fn:replace(article.creationDate, 'T', ' ')}</span>
                                    <h2 class="post__title">
                                        <a href="#" class="post__flow">Development</a><span class="post__title-arrow">&nbsp;&rarr;</span>
                                        <a href="${pageContext.request.contextPath}/articles/${article.id}" class="post__title_link"><c:out value="${article.title}" /></a>
                                    </h2>
                                    <div class="hubs">
                                        <img class="hub_icon" src="${pageContext.request.contextPath}/resources/images/hub-icon.png">
                                        <a href="${pageContext.request.contextPath}/hubs/${article.hub.id}/articles/" class="hub ">${article.hub.name}</a>
                                    </div>
                                </div>


                                <div class="post__body post__body_crop ">
                                    <div class="content html_format"><c:out value="${article.preview}" /></div>
                                </div>

                                <%@ include file="includes/postFooter.jspf" %>

                            </div>

                            <div style="padding-top: 50px"></div>
                        </c:forEach>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="message">
                        <fmt:message key="user_profile.label.have_no_articles" />
                    </div>
                </c:otherwise>
            </c:choose>
        </div>

    </div>

</div>
</body>

</html>
