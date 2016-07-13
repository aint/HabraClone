<%@page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

    <div class="div_centred">
        <a href="${pageContext.request.contextPath}/">
            <h1 class="page-header__title page-header__title_inline"><fmt:message key="index.title" /></h1>
        </a>
    </div>


    <sec:authorize access="!isAuthenticated()">
        <div class="div_centred">
            <c:if test="${not empty error_msg}" >
                <div class="error">
                    ${error_msg}
                </div>
            </c:if>
            <a id="modal_trigger" href="#modal_log" class="btn"><fmt:message key="header.link.sign_in" /></a>

            <a id="modal_trigger_r" href="#modal_reg" class="btn"><fmt:message key="header.link.sign_up" /></a>
        </div>
    </sec:authorize>
    <sec:authorize access="isAuthenticated()">
        <div style="text-align: right; margin-right: 30px">
            <form action="${pageContext.request.contextPath}/logout" method="post">
                <h3><fmt:message key="header.label.welcome_message" /> ${pageContext.request.userPrincipal.name}</h3>
                <input class="command_btns btn" type="submit" value="<fmt:message key="header.link.sign_out" />">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            </form>
        </div>
    </sec:authorize>

    <a href="${pageContext.request.contextPath}/locale/en"><img src="${pageContext.request.contextPath}/resources/images/flags/en.gif" alt="en"></a>
    <a href="${pageContext.request.contextPath}/locale/ru"><img src="${pageContext.request.contextPath}/resources/images/flags/ru.gif" alt="рус"></a>
    <a href="${pageContext.request.contextPath}/locale/uk"><img src="${pageContext.request.contextPath}/resources/images/flags/ua.gif" alt="укр"></a>

    <div id="modal_log" class="popupContainer" style="display:none;">
        <header class="popupHeader">
            <span class="header_title"><fmt:message key="login.title" /></span>
            <span class="modal_close"><i class="fa fa-times"></i></span>
        </header>

        <section class="popupBody">
            <div class="user_login">
                <form action="${pageContext.request.contextPath}/login" name="loginForm" method="post" onsubmit="return validateLoginForm()">
                    <label><fmt:message key="login.label.username" /></label>
                    <input type="text" name="username" />
                    <br/>
                    <label><fmt:message key="login.label.password" /></label>
                    <input type="password" name="password" />
                    <br/>
                    <label><input type="checkbox" name="remember-me" /> <fmt:message key="login.label.remember_me" /></label>
                    <br/>
                    <input class="action_btns btn btn_red" type="submit" value="<fmt:message key="login.button.login" />" />
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                </form>
            </div>
        </section>
    </div>


    <div id="modal_reg" class="popupContainer" style="display:none;">
        <header class="popupHeader">
            <span class="header_title"><fmt:message key="registration.title" /></span>
            <span class="modal_close"><i class="fa fa-times"></i></span>
        </header>

        <section class="popupBody">
            <div class="user_register">
                <form action="${pageContext.request.contextPath}/" name="regForm" method="post" onsubmit="return validateRegForm()">
                    <label><fmt:message key="registration.label.username" /></label>
                    <input type="text" name="username" />
                    <br/>

                    <label><fmt:message key="registration.label.email" /></label>
                    <input type="email" name="email" />
                    <br/>

                    <label><fmt:message key="registration.label.password" /></label>
                    <input type="password" name="password" />
                    <br/>

                    <input class="action_btns btn btn_red" type="submit" value="<fmt:message key="registration.button.sign_up" />" />
                </form>
            </div>
        </section>
    </div>


<script type="text/javascript">
    $("#modal_trigger").leanModal({top : 200, overlay : 0.6, closeButton: ".modal_close" });
    $(".user_login").show();

    $("#modal_trigger_r").leanModal({top : 200, overlay : 0.6, closeButton: ".modal_close" });
    $(".user_register").show();
</script>
