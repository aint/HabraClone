<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>

<head>
    <title><fmt:message key="articles.title" /></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/resources/images/favicon.png" sizes="32x32" />
    <script type="text/javascript"src="${pageContext.request.contextPath}/resources/js/validation.js"></script>
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
                    <span class="post__time_published"><c:out value="${fn:replace(article.creationDate, 'T', ' ')}" /></span>

                    <h2 class="post__title">
                        <a href="https://habrahabr.ru/flows/develop/" class="post__flow">Development</a><span class="post__title-arrow">&nbsp;&rarr;</span>
                        <a href="/articles/${article.id}" class="post__title_link"><c:out value="${article.title}" /></a>
                    </h2>

                    <div class="hubs">
                        <a href="https://habrahabr.ru/hub/iot_dev/" class="hub ">Test Hub 1</a>,
                        <a href="https://habrahabr.ru/hub/controllers/" class="hub ">Test Hub 2</a>
                    </div>
                </div>


                <div class="post__body post__body_crop ">
                    <div id="post_content" class="content html_format">${article.body}</div>
                </div>

            </div>
            <br>
            <br>
            <br>
            <br>
            <br>
        </div>
    </div>

    <h2 class="comment_title">
        <fmt:message key="display_article.label.comments" /> (${fn:length(comments)})
    </h2>
    <c:choose>
        <c:when test="${not empty comments}">
            <c:forEach items="${comments}" var="comment">


                <div class="info comments-list__item comment-item ">

                    <span class="comment-item__user-info">
                        <a href="/users/${comment.author.id}" class="comment-item__avatar">
                            <img src="${pageContext.request.contextPath}/resources/images/user-icon.png">
                        </a>
                        <a href="/users/${comment.author.id}" class="comment-item__username">${comment.author.userName}</a>
                    </span>

                    <time class="comment-item__time_published">
                        ${fn:replace(comment.creationDate, 'T', ' ')}
                    </time>

                    <div class="voting-wjt voting-wjt_comments">

                        <div class="voting-wjt__counter ">
                            <span class="voting-wjt__counter-score">${comment.rating}</span>
                        </div>

                        <button type="button" disabled="" class="voting-wjt__button voting-wjt__button_plus" title="Голосовать могут только зарегистрированные пользователи"><span>↑</span></button>

                        <button type="button" disabled="" class="voting-wjt__button voting-wjt__button_minus" title="Голосовать могут только зарегистрированные пользователи"><span>↓</span></button>
                    </div>
                </div>

                <div class="message html_format"><c:out value="${comment.body}" /></div>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <p><fmt:message key="display_article.label.have_no_comments" /></p>
        </c:otherwise>
    </c:choose>

</div>

<script type="text/javascript">
    $("body").children().each(function () {
        $(this).html( $(this).html().replace(/@pre-code@/g,"<pre><code>") );
        $(this).html( $(this).html().replace(/@code-pre@/g,"</code></pre>") );
    });
</script>

</body>
</html>
