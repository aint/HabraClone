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

    <jsp:include page="includes/header.jsp"/>

    <jsp:include page="includes/profileHeader.jsp"/>

    <div class="column-wrapper">
        <div class="content_left">
            <c:set var="commentTab" value="true" scope="request" />
            <jsp:include page="includes/userTabs.jsp" />
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
