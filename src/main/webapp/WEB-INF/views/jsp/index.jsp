<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>

<head>
    <title><fmt:message key="index.title" /></title>
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


    <div class="page-header page-header_inline">
        <h1 class="page-header__title page-header__title_inline"><fmt:message key="index.label.publications" /></h1>
    </div>

    <c:if test="${not empty TOP_ARTICLES}">
        <c:set var="ARTICLES" scope="page" value="${TOP_ARTICLES}" />
    </c:if>

    <div class="tabs">
        <ul class="tabs-menu tabs-menu_habrahabr">
            <li class="tabs-menu__item tabs-menu__item_inline">
                <a href="${pageContext.request.contextPath}/" class="tab-item ${(empty TOP_ARTICLES) ? "tab-item_current": ""}" >
                  <span class="tab-item__value">
                    <fmt:message key="index.label.all" />
                  </span>
                </a>
            </li>
            <li class="tabs-menu__item tabs-menu__item_inline">
                <a href="${pageContext.request.contextPath}/articles/top" class="tab-item ${(not empty TOP_ARTICLES) ? "tab-item_current": ""}" >
                  <span class="tab-item__value">
                    <fmt:message key="index.label.top" />
                  </span>
                </a>
            </li>
            <sec:authorize access="isAuthenticated()">
                <li class="tabs-menu__item tabs-menu__item_inline">
                    <a href="${pageContext.request.contextPath}/articles/add" class="tab-item" >
                      <span class="tab-item__value">
                        <fmt:message key="index.label.add_article" />
                      </span>
                    </a>
                </li>
            </sec:authorize>
        </ul>
    </div>





    <div class="posts_list">
        <div class="posts shortcuts_items">
            <c:forEach items="${ARTICLES}" var="article" varStatus="var">
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

                    <div class="post__footer" style="padding-top: 20px">
                        <ul class="postinfo-panel">
                            <li class="postinfo-panel__item">
                                <div class="voting-wjt voting-wjt_infopanel">
                                    <button type="button" disabled class="voting-wjt__button voting-wjt__button_plus">
                                        <span>&uarr;</span>
                                    </button>
                                    <div class="voting-wjt__counter voting-wjt__counter_positive ">
                                        <span class="voting-wjt__counter-score">${article.rating}</span>
                                    </div>
                                    <button type="button" disabled class="voting-wjt__button voting-wjt__button_minus">
                                        <span>&darr;</span>
                                    </button>
                                </div>
                            </li>
                            <li class="postinfo-panel__item">
                                <div class="views-count_post">
                                    <img src="${pageContext.request.contextPath}/resources/images/page_views.png" class="post_views_count">
                                    ${article.views}
                                </div>
                            </li>
                            <li class="postinfo-panel__item">
                                <div class="favorite-wjt favorite">
                                    <form action="${pageContext.request.contextPath}/articles/${article.id}/add-favorite" id="add-favorite-${article.id}" >
                                    </form>
                                    <button type="submit" form="add-favorite-${article.id}" class="favorite-wjt__button add">
                                        <img src="${pageContext.request.contextPath}/resources/images/favorites.png" class="post_favorites">
                                    </button>
                                    <span class="favorite-wjt__counter">${article.favorites}</span>
                                </div>
                            </li>
                            <li class="postinfo-panel__item post-author">
                                <a class="post-author__link" href="${pageContext.request.contextPath}/users/${article.author.username}/" >
                                    <img src="${pageContext.request.contextPath}/resources/images/user.png" class="post-author__pic"/>${article.author.username}
                                </a>
                            </li>
                            <li class="postinfo-panel__item postinfo-panel__item_comments">
                                <div class="post-comments">
                                    <img src="${pageContext.request.contextPath}/resources/images/comments.png" class="comments_pic">
                                    <a href="${pageContext.request.contextPath}/articles/${article.id}#comments" class="post-comments__link post-comments__link_all">
                                        ${fn:length(article.comments)}
                                    </a>
                                </div>
                            </li>
                        </ul>
                    </div>

                </div>

                <div style="padding-top: 50px"></div>
            </c:forEach>
        </div>
    </div>


</div>




<script type="text/javascript">
    $("#modal_trigger").leanModal({top : 200, overlay : 0.6, closeButton: ".modal_close" });
    $(".user_login").show();

    $("#modal_trigger_r").leanModal({top : 200, overlay : 0.6, closeButton: ".modal_close" });
    $(".user_register").show();
</script>

</body>
</html>
