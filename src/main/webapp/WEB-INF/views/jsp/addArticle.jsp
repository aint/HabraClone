<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title><fmt:message key="add_article.title" /></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/resources/images/favicon.png" sizes="32x32" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/validation.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/insertTags.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-1.11.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.leanModal.min.js"></script>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css" />
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/index_main.css" />
</head>
<body>
    <div class="container">

        <jsp:include page="includes/header.jsp"/>

        <div class="page-header">
            <h1 class="page-header__title"><fmt:message key="add_article.title" /></h1>
        </div>

        <div class="column-wrapper column-wrapper_content">
            <div class="content_left">
                <div class="sandbox_edit">

                    <form:form action="${pageContext.request.contextPath}/articles/add" method="post" modelAttribute="articleForm">

                        <div class="item hubs required">
                            <label for="hub_id">
                                <fmt:message key="add_article.label.select_hub" /><sup class="required_field">*</sup>
                            </label>
                            <div id="hubs_chosen" title="" style="width: 100%;">
                                <select name="hubId" size="1" id="hub_id">
                                    <c:forEach items="${requestScope.HUBS}" var="hub">
                                        <option value="${hub.id}">${hub.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <form:errors path="hubId" cssClass="error" />
                        </div>

                        <div class="item title required">
                            <label for="title">
                                <fmt:message key="add_article.label.title" /><sup class="required_field">*</sup>
                            </label>
                            <form:input path="title" id="title" name="title" type="text" />
                            <form:errors path="title" cssClass="error" />
                        </div>

                        <div class="item text required">
                            <label for="preview">
                                <fmt:message key="add_article.label.preview" /><sup class="required_field">*</sup>
                            </label>
                            <div class="editor">
                                <div class="text-holder">
                                    <form:textarea path="preview" name="preview" id="preview" cols="30" rows="4" style="height: 170px;"></form:textarea>
                                </div>
                            </div>
                            <form:errors path="preview" cssClass="error" />
                        </div>

                        <div class="item text required">
                            <label for="body">
                                <fmt:message key="add_article.label.body" /><sup class="required_field">*</sup>
                            </label>
                            <span id="tag_toolbar">
                                <input type="button" id="tag_bold" onclick="edInsertTag(body, 0);" value="b">
                                <input type="button" id="tag_italic" onclick="edInsertTag(body, 1);" value="i">
                                <input type="button" id="tag_del" onclick="edInsertTag(body, 2);" value="del">
                                <input type="button" id="tag_link" onclick="edInsertLink(body, 3);" value="link">
                                <input type="button" id="tag_block" onclick="edInsertTag(body, 4);" value="code">
                            </span><br>
                            <div class="editor">
                                <div class="text-holder">
                                    <form:textarea path="body" name="body" id="body" cols="30" rows="7" ></form:textarea>
                                </div>
                            </div>
                            <form:errors path="body" cssClass="error" />
                        </div>

                        <div class="item tags_string required">
                            <label for="keywords">
                                <fmt:message key="add_article.label.keywords" /><sup class="required_field">*</sup>
                            </label>
                            <form:input path="keywords" name="keywords" type="text" id="keywords" />
                            <form:errors path="keywords" cssClass="error" />
                        </div>

                        <div class="item tags_string required">
                            <div class="description">* - required</div>
                        </div>


                        <div class="buttons blue_buttons_panel">
                            <input class="btn btn_huge btn_green" value="<fmt:message key="add_article.button.add_article" />" type="submit">
                        </div>

                        <%--<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />--%>
                    </form:form>
                </div>
            </div>
        </div>

        <div>&zwnj;&zwnj; &zwnj;&zwnj;</div>
    </div>
</body>
</html>
