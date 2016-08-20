<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:choose>
    <c:when test="${not empty requestScope.profileTab}">
        <c:set var="profileTab" value="tab-item_current"/>
    </c:when>
    <c:when test="${not empty requestScope.commentTab}">
        <c:set var="commentTab" value="tab-item_current"/>
    </c:when>
    <c:when test="${not empty requestScope.FAVORITES}">
        <c:set var="favoriteTab" value="tab-item_current"/>
    </c:when>
    <c:when test="${empty requestScope.FAVORITES}">
        <c:set var="publicationTab" value="tab-item_current"/>
    </c:when>
</c:choose>


<div class="tabs">
    <ul class="tabs-menu tabs-menu_habrahabr">
        <li class="tabs-menu__item_inline">
            <a href="${pageContext.request.contextPath}/users/${requestScope.USER.username}" class="tab-item tab-item_stacked ${profileTab}">
                <fmt:message key="user_profile.label.profile" />
            </a>
        </li>
        <li class="tabs-menu__item_inline">
            <a href="${pageContext.request.contextPath}/users/${requestScope.USER.username}/articles/" class="tab-item tab-item_stacked ${publicationTab}">
                <strong>${requestScope.USER.articlesCount}</strong> <fmt:message key="user_profile.label.articles" />
            </a>
        </li>
        <li class="tabs-menu__item_inline">
            <a href="${pageContext.request.contextPath}/users/${requestScope.USER.username}/comments/" class="tab-item tab-item_stacked ${commentTab}">
                <strong>${requestScope.USER.commentsCount}</strong> <fmt:message key="user_profile.label.comments" />
            </a>
        </li>
        <li class="tabs-menu__item_inline">
            <a href="${pageContext.request.contextPath}/users/${requestScope.USER.username}/favorites/" class="tab-item tab-item_stacked ${favoriteTab}">
                <strong>${requestScope.USER.favoritesCount}</strong> <fmt:message key="user_profile.label.favorites" />
            </a>
        </li>
    </ul>
</div>