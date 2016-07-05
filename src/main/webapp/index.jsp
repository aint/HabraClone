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
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/index_main.css" />
</head>

<body>

<div class="container">
    <div style="text-align: center">
        <h1 class="page-header__title page-header__title_inline"><fmt:message key="index.title" /></h1>
    </div>

    <div style="text-align: center;">
        <span style="color: red;">
            ${error_msg}
        </span>
        <a id="modal_trigger" href="#modal_log" class="btn">Login</a>

        <a id="modal_trigger_r" href="#modal_reg" class="btn">Register</a>
    </div>

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
                <strong class="counter_new">+34</strong>
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
            <div class="post post_teaser shortcuts_item" id="post_304546">

                <div class="post__header">
                    <span class="post__time_published">сегодня в 14:35</span>

                    <h2 class="post__title">
                        <a href="https://habrahabr.ru/flows/develop/" class="post__flow">Разработка</a><span class="post__title-arrow">&nbsp;&rarr;</span>
                        <a href="https://habrahabr.ru/company/intel/blog/304546/" class="post__title_link">ZigBee и Intel Edison: практика автоматизации переговорных комнат</a>
                    </h2>

                    <div class="hubs">
                        <a href="https://habrahabr.ru/hub/iot_dev/" class="hub ">Разработка для интернета вещей</a>,
                        <a href="https://habrahabr.ru/hub/controllers/" class="hub ">Программирование микроконтроллеров</a>,
                        <a href="https://habrahabr.ru/company/intel/" class="hub ">Блог компании Intel</a>
                    </div>
                </div>


                <div class="post__body post__body_crop ">
                    <div class="content html_format">Во многих организациях комнаты для переговоров используют неэффективно. У такого положения дел есть две основных причины. Первая заключается в том, что некто, забронировав переговорную, может ей и не воспользоваться в назначенное время. Вторая причина – встреча закончилась раньше, чем было запланировано, все разошлись, но об этом не знают те, кому комната пригодилась бы.<br/>
                        <br/>
                        <a href="https://habrahabr.ru/company/intel/blog/304546/"><div style="text-align:center;"><img src="https://habrastorage.org/files/54c/ea4/f72/54cea4f727c147d8bdd2f8073d56290f.jpg" /></div></a><br/>
                        Мы создали интеллектуальную систему бронирования переговорных комнат (Smart Conference Room System, SCR) для того, чтобы помочь всем желающим с этими проблемами справиться.<br/>
                    </div>
                </div>

            </div>

            <br>
            <br>
            <br>
            <br>
            <br>

            <div class="post post_teaser shortcuts_item" id="post_304546">

                <div class="post__header">
                    <span class="post__time_published">сегодня в 14:35</span>

                    <h2 class="post__title">
                        <a href="https://habrahabr.ru/flows/develop/" class="post__flow">Разработка</a><span class="post__title-arrow">&nbsp;&rarr;</span>
                        <a href="https://habrahabr.ru/company/intel/blog/304546/" class="post__title_link">ZigBee и Intel Edison: практика автоматизации переговорных комнат</a>
                    </h2>

                    <div class="hubs">
                        <a href="https://habrahabr.ru/hub/iot_dev/" class="hub ">Разработка для интернета вещей</a>,
                        <a href="https://habrahabr.ru/hub/controllers/" class="hub ">Программирование микроконтроллеров</a>,
                        <a href="https://habrahabr.ru/company/intel/" class="hub ">Блог компании Intel</a>
                    </div>
                </div>


                <div class="post__body post__body_crop ">
                    <div class="content html_format">Во многих организациях комнаты для переговоров используют неэффективно. У такого положения дел есть две основных причины. Первая заключается в том, что некто, забронировав переговорную, может ей и не воспользоваться в назначенное время. Вторая причина – встреча закончилась раньше, чем было запланировано, все разошлись, но об этом не знают те, кому комната пригодилась бы.<br/>
                        <br/>
                        <a href="https://habrahabr.ru/company/intel/blog/304546/"><div style="text-align:center;"><img src="https://habrastorage.org/files/54c/ea4/f72/54cea4f727c147d8bdd2f8073d56290f.jpg" /></div></a><br/>
                        Мы создали интеллектуальную систему бронирования переговорных комнат (Smart Conference Room System, SCR) для того, чтобы помочь всем желающим с этими проблемами справиться.<br/>
                    </div>
                </div>

            </div>

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
