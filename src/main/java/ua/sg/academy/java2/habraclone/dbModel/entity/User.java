package ua.sg.academy.java2.habraclone.dbModel.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "User")
public class User implements IEntity {
    public static final Language DEFAULT_LANGUAGE = Language.ENGLISH;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, unique = true)
    private String userName;
    @Column
    private String fullName;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column
    private String about;
    @Column
    private String location;
    @Column
    private int rating;
    @Column
    private boolean admin;
    @Column
    private boolean activated;
    @Column
    private LocalDate birthday;
    @Column(nullable = false)
    private LocalDateTime lastLoginTime;
    @Column(nullable = false)
    private LocalDateTime registrationDate;
    @Column
    private LocalDateTime banExpirationDate;
    @Column
    private Language language = DEFAULT_LANGUAGE;
    @OneToMany(mappedBy = "author", /*fetch=FetchType.EAGER,*/ cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Article> articles = new ArrayList<>();
    @Column
    private int articlesCount;
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();
    @Column
    private int commentsCount;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Article> favorites = new ArrayList<>();
    @Column
    private int favoritesCount;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Hub> hubs = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    private Set<UserRole> roles = new HashSet<>();

    public User() {
    }

    public User(String email, String userName, String password) {
        this.email = email;
        this.userName = userName;
        this.password = password;
    }

    public User(String email, String userName, String password, LocalDateTime lastLoginTime, LocalDateTime registrationDate) {
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.lastLoginTime = lastLoginTime;
        this.registrationDate = registrationDate;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public LocalDateTime getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(LocalDateTime lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public LocalDateTime getBanExpirationDate() {
        return banExpirationDate;
    }

    public void setBanExpirationDate(LocalDateTime banExpirationDate) {
        this.banExpirationDate = banExpirationDate;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Article> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Article> favorites) {
        this.favorites = favorites;
    }

    public List<Hub> getHubs() {
        return hubs;
    }

    public void setHubs(List<Hub> hubs) {
        this.hubs = hubs;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
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
