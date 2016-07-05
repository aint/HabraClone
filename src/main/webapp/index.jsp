<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${pageContext.response.locale}" scope="session" />

<html>

<head>
    <title><fmt:message key="index.title" /></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/resources/images/favicon.png" sizes="32x32" />
    <script type="text/javascript"src="${pageContext.request.contextPath}/resources/js/validation.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-1.11.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.leanModal.min.js"></script>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css" />
</head>

<body>

<div class="container">
    <h1><fmt:message key="index.title" /></h1>
    <div style="text-align: center;">
        <span style="color: red;">
            ${error_msg}
        </span>
    </div>
    <a id="modal_trigger" href="#modal_log" class="btn">Login</a>

    <a id="modal_trigger_r" href="#modal_reg" class="btn">Register</a>

    <div id="modal_log" class="popupContainer" style="display:none;">
        <header class="popupHeader">
            <span class="header_title">Login</span>
            <span class="modal_close"><i class="fa fa-times"></i></span>
        </header>

        <section class="popupBody">
            <div class="user_login">
                <form name="loginForm" method="post" onsubmit="return validateLoginForm()">
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
                <form name="regForm" method="post" onsubmit="return validateRegForm()">
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
</div>

<script type="text/javascript">
    $("#modal_trigger").leanModal({top : 200, overlay : 0.6, closeButton: ".modal_close" });
    $(".user_login").show();

    $("#modal_trigger_r").leanModal({top : 200, overlay : 0.6, closeButton: ".modal_close" });
    $(".user_register").show();
</script>

</body>
</html>
