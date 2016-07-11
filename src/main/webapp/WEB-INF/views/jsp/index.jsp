<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>

<head>
    <title><fmt:message key="index.title" /></title>
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


    <div class="page-header page-header_inline">
        <h1 class="page-header__title page-header__title_inline">Публікації</h1>
    </div>

    <div class="tabs">
        <ul class="tabs-menu tabs-menu_habrahabr">
            <li class="tabs-menu__item tabs-menu__item_inline">
                <a href="https://habrahabr.ru/all/" class="tab-item "  >
              <span class="tab-item__value">
                Все підряд&nbsp;
                <strong class="counter"></strong>
                <strong class="counter_new">+3</strong>
              </span>
                </a>
            </li>
            <li class="tabs-menu__item tabs-menu__item_inline">
                <a href="https://habrahabr.ru/top/" class="tab-item "  >
              <span class="tab-item__value">
                Найкращі&nbsp;
                <strong class="counter"></strong>

              </span>
                </a>
            </li>
            <li class="tabs-menu__item tabs-menu__item_inline">
                <a href="https://habrahabr.ru/interesting/" class="tab-item tab-item_current"  >
              <span class="tab-item__value">
                Цікаві&nbsp;
                <strong class="counter"></strong>

              </span>
                </a>
            </li>
        </ul>
    </div>





    <div class="posts_list">
        <div class="posts shortcuts_items">
            <c:forEach items="${requestScope.ARTICLES}" var="article" varStatus="var">
                <div class="post post_teaser shortcuts_item">

                    <div class="post__header">
                        <span class="post__time_published">${fn:replace(article.creationDate, 'T', ' ')}</span>

                        <h2 class="post__title">
                            <a href="https://habrahabr.ru/flows/develop/" class="post__flow">Development</a><span class="post__title-arrow">&nbsp;&rarr;</span>
                            <a href="${pageContext.request.contextPath}/articles/${article.id}" class="post__title_link"><c:out value="${article.title}" /></a>
                        </h2>

                        <div class="hubs">
                            <a href="https://habrahabr.ru/hub/iot_dev/" class="hub ">Test Hub 1</a>,
                            <a href="https://habrahabr.ru/hub/controllers/" class="hub ">Test Hub 2</a>
                        </div>
                    </div>


                    <div class="post__body post__body_crop ">
                        <div class="content html_format"><c:out value="${article.preview}" /></div>
                    </div>

                </div>
                <br>
                <br>
                <br>
                <br>
                <br>
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