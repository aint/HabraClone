<%@page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${pageContext.response.locale}" scope="session" />

    <c:choose>
        <c:when test="${empty sessionScope.user_session}">
            <div style="text-align: center;">
                <span style="color: red;">
                    ${error_msg}<br>
                </span>
                <a id="modal_trigger" href="#modal_log" class="btn"><fmt:message key="header.link.sign_in" /></a>

                <a id="modal_trigger_r" href="#modal_reg" class="btn"><fmt:message key="header.link.sign_up" /></a>
            </div>
        </c:when>

        <c:otherwise>
            <div style="text-align: right; margin-right: 30px">
                <form action="/logout" method="post">
                    <h3><fmt:message key="header.label.welcome_message" /> ${sessionScope.user_session}</h3>
                    <input class="command_btns btn" type="submit" name="log_button" value="Logout">
                </form>
            </div>
        </c:otherwise>
    </c:choose>

    <a href="#"><img src="${pageContext.request.contextPath}/resources/images/flags/en.gif" alt="en"></a>
    <a href="#"><img src="${pageContext.request.contextPath}/resources/images/flags/ru.gif" alt="рус"></a>
    <a href="#"><img src="${pageContext.request.contextPath}/resources/images/flags/ua.gif" alt="укр"></a>

    <div id="modal_log" class="popupContainer" style="display:none;">
        <header class="popupHeader">
            <span class="header_title">Login</span>
            <span class="modal_close"><i class="fa fa-times"></i></span>
        </header>

        <section class="popupBody">
            <div class="user_login">
                <form action="/login" name="loginForm" method="post" onsubmit="return validateLoginForm()">
                    <label>Email</label>
                    <input type="text" name="email" />
                    <br />

                    <label>Password</label>
                    <input type="password" name="password" />
                    <br />

                    <input class="action_btns btn btn_red" name="log_button" type="submit" value="Login" />
                </form>
            </div>
        </section>
    </div>


    <div id="modal_reg" class="popupContainer" style="display:none;">
        <header class="popupHeader">
            <span class="header_title">Register</span>
            <span class="modal_close"><i class="fa fa-times"></i></span>
        </header>

        <section class="popupBody">
            <div class="user_register">
                <form action="/index" name="regForm" method="post" onsubmit="return validateRegForm()">
                    <label>Full Name</label>
                    <input type="text" name="username" />
                    <br />

                    <label>Email Address</label>
                    <input type="email" name="email" />
                    <br />

                    <label>Password</label>
                    <input type="password" name="password" />
                    <br />

                    <input class="action_btns btn btn_red" name="log_button" type="submit" value="Register" />
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
