package ua.sg.academy.java2.habraclone.webController.rest.json;

import ua.sg.academy.java2.habraclone.model.Language;
import ua.sg.academy.java2.habraclone.model.User;

import java.time.ZoneId;
import java.util.Date;

public class UserJson {

    private long id;
    private String username;
    private String email;
    private String fullName;
    private String password;
    private String about;
    private String location;
    private int rating;
    private boolean admin;
    private boolean enabled;
    private Date birthday;
    private Date lastLoginTime;
    private Date registrationDate;
    private Date banExpirationDate;
    private Language language;
    private int articlesCount;
    private int commentsCount;
    private int favoritesCount;

    public UserJson() {
    }

    public UserJson(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.fullName = user.getFullName();
        this.password = user.getPassword();
        this.about = user.getAbout();
        this.location = user.getLocation();
        this.rating = user.getRating();
        this.admin = user.isAdmin();
        this.enabled = user.isEnabled();
        if (user.getBirthday() != null) {
            this.birthday = Date.from(user.getBirthday().atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
        this.lastLoginTime = Date.from(user.getLastLoginTime().atZone(ZoneId.systemDefault()).toInstant());
        this.registrationDate = Date.from(user.getRegistrationDate().atZone(ZoneId.systemDefault()).toInstant());
        if (user.getBanExpirationDate() != null) {
            this.banExpirationDate = Date.from(user.getBanExpirationDate().atZone(ZoneId.systemDefault()).toInstant());
        }
        this.language = user.getLanguage();
        this.articlesCount = user.getArticlesCount();
        this.commentsCount = user.getCommentsCount();
        this.favoritesCount = user.getFavoritesCount();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Date getBanExpirationDate() {
        return banExpirationDate;
    }

    public void setBanExpirationDate(Date banExpirationDate) {
        this.banExpirationDate = banExpirationDate;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public int getArticlesCount() {
        return articlesCount;
    }

    public void setArticlesCount(int articlesCount) {
        this.articlesCount = articlesCount;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public int getFavoritesCount() {
        return favoritesCount;
    }

    public void setFavoritesCount(int favoritesCount) {
        this.favoritesCount = favoritesCount;
    }
}
