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

    <div class="profile-header">
        <div class="profile-header__summary author-info author-info_profile ">
            <a href="${pageContext.request.contextPath}/users/${USER.username}" class="author-info__image">
                <img src="${pageContext.request.contextPath}/resources/images/user.png">
            </a>
            <div class="author-info__desc">
                <div class="author-info__username">
                    <a href="${pageContext.request.contextPath}/users/${USER.username}">
                        ${USER.username}
                    </a>
                </div>
                <div class="author-info__specialization">${USER.fullName}</div>
            </div>
        </div>
        <div class="profile-header__stats">
            <div class="karma__widjet voting-wjt voting-wjt_user-profile js-karma ">
                <div class="voting-wjt__counter js-karma-mark voting-wjt__counter_positive ">
                    <div class="voting-wjt__counter-score js-karma_num">${USER.rating / 2}</div>
                    <div class="voting-wjt__label"><fmt:message key="user_profile.label.karma" /></div>
                </div>
            </div>
            <div class="statistic statistic_user-rating">
                <div class="statistic__value statistic__value_magenta">${USER.rating}</div>
                <div class="statistic__label"><fmt:message key="user_profile.label.rating" /></div>
            </div>
        </div>
    </div>

    <div class="column-wrapper">
        <div class="content_left">
            <div class="tabs">
                <ul class="tabs-menu tabs-menu_habrahabr">
                    <li class="tabs-menu__item_inline">
                        <a href="${pageContext.request.contextPath}/users/${USER.username}" class="tab-item tab-item_stacked">
                            <fmt:message key="user_profile.label.profile" />
                        </a>
                    </li>
                    <li class="tabs-menu__item_inline">
                        <a href="${pageContext.request.contextPath}/users/${USER.username}/articles/" class="tab-item tab-item_stacked ${(empty FAVORITES) ? "tab-item_current": ""}">
                            <strong>${fn:length(USER.articles)}</strong> <fmt:message key="user_profile.label.articles" />
                        </a>
                    </li>
                    <li class="tabs-menu__item_inline">
                        <a href="${pageContext.request.contextPath}/users/${USER.username}/comments/" class="tab-item tab-item_stacked ">
                            <strong>${fn:length(USER.comments)}</strong> <fmt:message key="user_profile.label.comments" />
                        </a>
                    </li>
                    <li class="tabs-menu__item_inline">
                        <a href="${pageContext.request.contextPath}/users/${USER.username}/favorites/" class="tab-item tab-item_stacked ${(empty FAVORITES) ? "" : "tab-item_current"}">
                            <strong>${fn:length(USER.favorites)}</strong> <fmt:message key="user_profile.label.favorites" />
                        </a>
                    </li>
                </ul>
            </div>
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
