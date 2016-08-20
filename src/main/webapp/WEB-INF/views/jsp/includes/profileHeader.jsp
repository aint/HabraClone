<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="profile-header">
    <div class="profile-header__summary author-info author-info_profile ">
        <a href="${pageContext.request.contextPath}/users/${requestScope.USER.username}" class="author-info__image">
            <img src="${pageContext.request.contextPath}/resources/images/user.png">
        </a>
        <div class="author-info__desc">
            <div class="author-info__username">
                <a href="${pageContext.request.contextPath}/users/${requestScope.USER.username}">
                    ${requestScope.USER.username}
                </a>
            </div>
            <div class="author-info__specialization">${requestScope.USER.fullName}</div>
        </div>
    </div>
    <div class="profile-header__stats">
        <div class="karma__widjet voting-wjt voting-wjt_user-profile js-karma ">
            <div class="voting-wjt__counter js-karma-mark voting-wjt__counter_positive ">
                <div class="voting-wjt__counter-score js-karma_num">${requestScope.USER.rating / 2}</div>
                <div class="voting-wjt__label"><fmt:message key="user_profile.label.karma" /></div>
            </div>
        </div>
        <div class="statistic statistic_user-rating">
            <div class="statistic__value statistic__value_magenta">${requestScope.USER.rating}</div>
            <div class="statistic__label"><fmt:message key="user_profile.label.rating" /></div>
        </div>
    </div>
</div>