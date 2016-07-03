package ua.sg.academy.java2.habraclone.dbModel.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "Comment")
public class Comment implements IEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, length = 11)
    private long id;
    @Column(name = "body", columnDefinition = "TEXT", nullable = false)
    private String body;
    @Column(name = "rating", nullable = false)
    private int rating;
    @Column(name = "creation_date", columnDefinition = "DATETIME", nullable = false)
    private LocalDateTime creationDate;
    @ManyToOne
    private User author;
    @ManyToOne
    private Article article;

    public Comment() {
    }

    public Comment(String body, User author, Article article) {
        this.body = body;
        this.author = author;
        this.article = article;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
