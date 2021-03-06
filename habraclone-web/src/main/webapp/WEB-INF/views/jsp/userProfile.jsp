<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>

<head>
    <title>${USER.username}</title>
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
            <c:set var="profileTab" value="true" scope="request" />
            <jsp:include page="includes/userTabs.jsp" />

            <div class="user_profile">

                <jsp:useBean id="now" class="java.util.Date"/>
                <fmt:parseDate value="${fn:replace(USER.banExpirationDate, 'T', ' ')}" pattern="yyyy-MM-dd HH:mm" var="banExpDate" type="both" />
                <c:choose>
                    <c:when test="${banExpDate gt now}">
                        <dl style="color: red">
                            <dt><fmt:message key="user_profile.label.ban_expiration_date" /></dt>
                            <dd>
                                <fmt:formatDate pattern="dd MMMM yyyy HH:mm" value="${banExpDate}" />,&emsp;
                                <a style="color: #009926" href="${pageContext.request.contextPath}/users/${USER.username}/unban">REMOVE BAN</a>
                            </dd>
                        </dl>
                    </c:when>
                    <c:otherwise>
                        <sec:authorize access="hasAuthority('ADMIN')">
                            <dl>
                                <dt>Ban for 5 days</dt>
                                <dd>
                                    <%--<input type="number" value="5">--%>
                                    <a style="color: red" href="${pageContext.request.contextPath}/users/${USER.username}/ban">BAN</a>
                                </dd>
                            </dl>
                        </sec:authorize>
                    </c:otherwise>
                </c:choose>

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
                        <dt><fmt:message key="user_profile.label.subscription" /></dt>
                        <dd>
                            <ul class="grey" id="hubs_data_items">
                                <c:forEach items="${USER.hubs}" var="hub" varStatus="var">
                                    <li class="">
                                        <img class="hub_icon" src="${pageContext.request.contextPath}/resources/images/hub-icon.png">
                                        <a class="" href="${pageContext.request.contextPath}/hubs/${hub.id}/articles">${hub.name}</a>
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
