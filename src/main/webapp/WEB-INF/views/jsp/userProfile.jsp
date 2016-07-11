<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
</head>

<body>

<div class="container">

    <jsp:include page="header.jsp"/>

    <div class="profile-header">
        <div class="profile-header__summary author-info author-info_profile ">
            <a href="${pageContext.request.contextPath}/users/${USER.userName}" class="author-info__image">
                <img src="${pageContext.request.contextPath}/resources/images/user.png">
            </a>
            <div class="author-info__desc">
                <div class="author-info__username">
                    <a href="${pageContext.request.contextPath}/users/${USER.userName}">
                        ${USER.userName}
                    </a>
                </div>
                <div class="author-info__specialization">Пользователь</div>
            </div>
        </div>
        <div class="profile-header__stats">
            <div class="karma__widjet voting-wjt voting-wjt_user-profile js-karma ">
                <div class="voting-wjt__counter js-karma-mark voting-wjt__counter_positive ">
                    <div class="voting-wjt__counter-score js-karma_num">${USER.rating / 2}</div>
                    <div class="voting-wjt__label"><fmt:message key="user_profile.label.karma" /></div>
                </div>
            </div>
            <div class="statistic statistic_user-rating" title="Рейтинг пользователя">
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
                        <a href="${pageContext.request.contextPath}/users/${USER.userName}" class="tab-item tab-item_stacked tab-item_current">
                            <fmt:message key="user_profile.label.profile" />
                        </a>
                    </li>
                    <li class="tabs-menu__item_inline">
                        <a href="${pageContext.request.contextPath}/users/${USER.userName}/topics/" class="tab-item tab-item_stacked ">
                            <strong>${fn:length(USER.articles)}</strong> <fmt:message key="user_profile.label.articles" />
                        </a>
                    </li>
                    <li class="tabs-menu__item_inline">
                        <a href="${pageContext.request.contextPath}/users/${USER.userName}/comments/" class="tab-item tab-item_stacked ">
                            <strong>${fn:length(USER.comments)}</strong> <fmt:message key="user_profile.label.comments" />
                        </a>
                    </li>
                    <li class="tabs-menu__item_inline">
                        <a href="${pageContext.request.contextPath}/users/${USER.userName}/favorites/" class="tab-item tab-item_stacked ">
                            <strong>${fn:length(USER.favorites)}</strong> <fmt:message key="user_profile.label.favorites" />
                        </a>
                    </li>
                </ul>
            </div>
            <div class="user_profile">
                <dl>
                    <dt><fmt:message key="user_profile.label.ranking" /></dt>
                    <dd>
                        <a href="${pageContext.request.contextPath}/users/">
                            ${USER_POSITION}<fmt:message key="user_profile.label.position" />
                        </a>
                    </dd>
                </dl>

                <c:if test="${not empty USER.birthday}">
                    <dl>
                        <dt><fmt:message key="user_profile.label.birthday" /></dt>
                        <dd class="bday">
                            <fmt:parseDate value="${USER.birthday}" pattern="yyyy-MM-dd" var="parsedBirthday" />
                            <fmt:formatDate value="${parsedBirthday}" pattern="dd MMMM yyyy" />
                        </dd>
                    </dl>
                </c:if>

                <c:if test="${not empty USER.location}">
                    <dl>
                        <dt><fmt:message key="user_profile.label.location" /></dt>
                        <dd>${USER.location}</dd>
                    </dl>
                </c:if>

                <c:if test="${not empty USER.about}">
                    <dl>
                        <dt><fmt:message key="user_profile.label.about" /></dt>
                        <dd>${USER.about}</dd>
                    </dl>
                </c:if>

                <c:if test="${not empty USER.hubs}">
                    <dl class="hubs_list">
                        <dt>Подписан на:</dt>
                        <dd>
                            <ul class="grey" id="hubs_data_items">
                                <c:forEach items="${USER.hubs}" var="hub" varStatus="var">
                                    <li class="">
                                        <img class="hub_icon" src="${pageContext.request.contextPath}/resources/images/hub-icon.png">
                                        <a class="" href="${pageContext.request.contextPath}/hubs/${hub.name}/">${hub.name}</a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </dd>
                    </dl>
                </c:if>

                <dl>
                    <dt><fmt:message key="user_profile.label.registration_date" /></dt>
                    <dd class="grey">
                        <fmt:parseDate value="${fn:replace(USER.registrationDate, 'T', ' ')}" pattern="yyyy-MM-dd HH:mm" var="registrationDate" type="both" />
                        <fmt:formatDate pattern="dd MMMM yyyy HH:mm" value="${registrationDate}" />
                    </dd>
                </dl>

                <dl>
                    <dt><fmt:message key="user_profile.label.activity" /></dt>
                    <dd>
                        <fmt:message key="user_profile.label.last_login_time" />
                        <fmt:parseDate value="${fn:replace(USER.lastLoginTime, 'T', ' ')}" pattern="yyyy-MM-dd HH:mm" var="lastLogDate" type="both" />
                        <fmt:formatDate pattern="dd MMMM yyyy HH:mm" value="${lastLogDate}" />
                    </dd>
                </dl>
            </div>
        </div>
    </div>

    <div>&zwnj;&zwnj; &zwnj;&zwnj;</div>
</div>
</body>

</html>
