<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>

<head>
    <title><c:out value="${article.title}" /></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/resources/images/favicon.png" sizes="32x32" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/validation.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-1.11.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.leanModal.min.js"></script>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css" />
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/index_main.css" />

    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/github.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/highlight.pack.js"></script>
    <script>hljs.initHighlightingOnLoad();</script>

</head>

<body>

<div class="container">

    <jsp:include page="header.jsp"/>

    <div class="posts_list">
        <div class="posts shortcuts_items">
            <div class="post post_teaser shortcuts_item">

                <div class="post__header">
                    <span class="post__time_published">${fn:replace(article.creationDate, 'T', ' ')}"</span>
                    <h2 class="post__title">
                        <a href="#" class="post__flow">Development</a><span class="post__title-arrow">&nbsp;&rarr;</span>
                        <a href="${pageContext.request.contextPath}/articles/${article.id}" class="post__title_link"><c:out value="${article.title}" /></a>
                    </h2>
                    <div class="hubs">
                        <img class="hub_icon" src="${pageContext.request.contextPath}/resources/images/hub-icon.png">
                        <a href="${pageContext.request.contextPath}/hubs/${article.hub.id}/articles/" class="hub ">${article.hub.name}</a>
                        <sec:authorize access="hasAnyRole('ROLE_ADMIN')">
                            &emsp;
                            <a href="${pageContext.request.contextPath}/articles/${article.id}/delete" style="color: red">DELETE</a>
                        </sec:authorize>
                    </div>
                </div>


                <div class="post__body post__body_crop ">
                    <div id="post_content" class="content html_format"><c:out value="${article.body}" /></div>
                </div>

                <div class="post__footer" style="padding-top: 20px">
                    <ul class="postinfo-panel">
                        <li class="postinfo-panel__item">
                            <div class="voting-wjt voting-wjt_infopanel">
                                <button type="button" onclick="location.href='/articles/${article.id}/vote/plus';" class="voting-wjt__button voting-wjt__button_plus">
                                    <span>&uarr;</span>
                                </button>
                                <div class="voting-wjt__counter voting-wjt__counter_positive ">
                                    <span class="voting-wjt__counter-score">${article.rating}</span>
                                </div>
                                <button type="button" onclick="location.href='/articles/${article.id}/vote/minus';" class="voting-wjt__button voting-wjt__button_minus">
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
                            <a class="post-author__link" href="${pageContext.request.contextPath}/users/${article.author.userName}/" >
                                <img src="${pageContext.request.contextPath}/resources/images/user.png" class="post-author__pic"/>${article.author.userName}
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
        </div>
    </div>

    <h2 class="comment_title div_centred">
        <fmt:message key="display_article.label.comments" /> (${fn:length(article.comments)})
    </h2>
    <c:choose>
        <c:when test="${not empty article.comments}">
            <c:forEach items="${article.comments}" var="comment">

                <div class="info comments-list__item comment-item ">
                    <span class="comment-item__user-info">
                        <a href="${pageContext.request.contextPath}/users/${comment.author.userName}" class="comment-item__avatar">
                            <img src="${pageContext.request.contextPath}/resources/images/user-icon.png">
                        </a>
                        <a href="${pageContext.request.contextPath}/users/${comment.author.userName}" class="comment-item__username">${comment.author.userName}</a>
                    </span>

                    <time class="comment-item__time_published">
                        ${fn:replace(comment.creationDate, 'T', ' ')}
                        <sec:authorize access="hasAnyRole('ROLE_ADMIN')">
                            &emsp;
                            <a style="color: red" href="#" onclick="document.forms['deleteComment-${comment.id}'].submit(); return false;">DELETE</a>
                            <form action="${pageContext.request.contextPath}/comments/${comment.id}/delete" name="deleteComment-${comment.id}" method="post">
                                <input type="hidden" name="articleId" value="${article.id}">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                            </form>
                        </sec:authorize>
                    </time>

                    <div class="voting-wjt voting-wjt_comments">

                        <div class="voting-wjt__counter ">
                            <span class="voting-wjt__counter-score">${comment.rating}</span>
                        </div>

                        <button type="button" disabled="" class="voting-wjt__button voting-wjt__button_plus"><span>↑</span></button>
                        <button type="button" disabled="" class="voting-wjt__button voting-wjt__button_minus"><span>↓</span></button>
                    </div>
                </div>

                <div class="message html_format"><c:out value="${comment.body}" /></div>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <div class="message no_comments_yet">
                <fmt:message key="display_article.label.have_no_comments" />
            </div>
        </c:otherwise>
    </c:choose>

    <sec:authorize access="isAuthenticated()">
        <div class="comment_title"><fmt:message key="display_article.label.leave_comment" /> </div>
        <form action="${pageContext.request.contextPath}/comments/add" method="post">
            <div class="comment_editor">
                <textarea cols="30" rows="10" name="comment_body" class="comment_body"></textarea>
            </div>
            <input type="submit" class="submit_comment" disabled="disabled" value="Написать">
            <input type="hidden" name="article_id" value="${article.id}">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        </form>
    </sec:authorize>

</div>

<script type="text/javascript">
    $("body").children().each(function () {
        $(this).html( $(this).html().replace(/@pre-code@/g,"<pre><code>") );
        $(this).html( $(this).html().replace(/@code-pre@/g,"</code></pre>") );
    });

    $(".comment_body").on('input', function() {
        if ($(this).val().length > 0) {
            $(".submit_comment").removeAttr('disabled');
        } else {
            $(".submit_comment").attr("disabled", "disabled");
        }
    });
</script>

</body>
</html>
