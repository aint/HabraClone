package ua.sg.academy.java2.habraclone.webController.rest.json;

import ua.sg.academy.java2.habraclone.model.Article;

import java.time.ZoneId;
import java.util.Date;

public class ArticleJson {

    private long id;
    private String title;
    private String preview;
    private String body;
    private int rating;
    private String keywords;
    private int views;
    private int favorites;
    private Date creationDate;
    private String authorUsername;
    private String hubName;
    private int commentCount;

    public ArticleJson() {
    }

    public ArticleJson(Article article) {
        this.id = article.getId();
        this.title= article.getTitle();
        this.preview = article.getPreview();
        this.body = article.getBody();
        this.rating = article.getRating();
        this.keywords = article.getKeywords();
        this.views = article.getViews();
        this.favorites = article.getFavorites();
        this.creationDate = Date.from(article.getCreationDate().atZone(ZoneId.systemDefault()).toInstant());
        this.authorUsername = article.getAuthor().getUsername();
        this.hubName = article.getHub().getName();
        this.commentCount = article.getComments().size();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getFavorites() {
        return favorites;
    }

    public void setFavorites(int favorites) {
        this.favorites = favorites;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }

    public String getHubName() {
        return hubName;
    }

    public void setHubName(String hubName) {
        this.hubName = hubName;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
}
