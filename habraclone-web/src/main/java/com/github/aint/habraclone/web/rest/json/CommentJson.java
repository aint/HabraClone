package com.github.aint.habraclone.web.rest.json;

import com.github.aint.habraclone.data.model.Comment;

import java.time.ZoneId;
import java.util.Date;

public class CommentJson {

    private long id;
    private String body;
    private int rating;
    private Date creationDate;
    private String author;
    private long articleId;

    public CommentJson() {
    }

    public CommentJson(Comment comment) {
        this.id = comment.getId();
        this.body = comment.getBody();
        this.rating = comment.getRating();
        this.creationDate = Date.from(comment.getCreationDate().atZone(ZoneId.systemDefault()).toInstant());
        this.author = comment.getAuthor().getUsername();
        this.articleId = comment.getArticle().getId();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getArticleId() {
        return articleId;
    }

    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }
}
