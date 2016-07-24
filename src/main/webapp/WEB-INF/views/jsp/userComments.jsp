<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>

<head>
    <title>${USER.username} <fmt:message key="user_profile.label.comments" /></title>
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
                        <a href="${pageContext.request.contextPath}/users/${USER.username}/articles/" class="tab-item tab-item_stacked">
                            <strong>${fn:length(USER.articles)}</strong> <fmt:message key="user_profile.label.articles" />
                        </a>
                    </li>
                    <li class="tabs-menu__item_inline">
                        <a href="${pageContext.request.contextPath}/users/${USER.username}/comments/" class="tab-item tab-item_stacked tab-item_current">
                            <strong>${fn:length(USER.comments)}</strong> <fmt:message key="user_profile.label.comments" />
                        </a>
                    </li>
                    <li class="tabs-menu__item_inline">
                        <a href="${pageContext.request.contextPath}/users/${USER.username}/favorites/" class="tab-item tab-item_stacked ">
                            <strong>${fn:length(USER.favorites)}</strong> <fmt:message key="user_profile.label.favorites" />
                        </a>
                    </li>
                </ul>
            </div>
        </div>

        <c:choose>
            <c:when test="${not empty USER.comments}">
                <c:forEach items="${USER.comments}" var="comment" varStatus="var">
                    <div class="user_comments">
                        <div class="comments_list" style="margin-top:0;">
                            <div class="comment_item_plain">
                                <div class="post_info">
                                    <a href="${pageContext.request.contextPath}/articles/${comment.article.id}" class="grey">${comment.article.title}</a>
                                </div>

                                <div class="comment_item">
                                    <div class="comment-item">
                                        <a href="${pageContext.request.contextPath}/users/${USER.username}" class="comment-item__avatar">
                                            <img src="${pageContext.request.contextPath}/resources/images/user.png" class="comment-item__avatar-img">
                                        </a>

                                        <a href="${pageContext.request.contextPath}/users/${USER.username}" class="comment-item__username">${USER.username}</a>
                                        <time class="comment-item__time_pubished">${fn:replace(comment.creationDate, 'T', ' ')}</time>

                                        <div class="voting voting-wjt voting-wjt_comments">
                                            <div class="voting-wjt__counter">
                                                <span class="voting-wjt__counter-score ">${comment.rating}</span>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="message html_format ">${comment.body}</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div class="message">
                    <fmt:message key="user_profile.label.have_no_comments" />
                </div>
            </c:otherwise>
        </c:choose>
    </div>

</div>
</body>

</html>
