package ua.sg.academy.java2.habraclone.dbModel.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Article")
public class Article implements IEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, length = 11)
    private long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "preview", columnDefinition = "TEXT", nullable = false)
    private String preview;
    @Column(name = "body", columnDefinition = "TEXT", nullable = false)
    private String body;
    @Column(name = "rating", nullable = false)
    private int rating;
    @Column(name = "keywords")
    private String keywords;
    @Column(name = "views", nullable = false)
    private int views;
    @Column(name = "creation_date", columnDefinition = "DATETIME", nullable = false)
    private LocalDateTime creationDate;
    @ManyToOne
    private User author;
    @ManyToOne
    private Hub hub;
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public Article() {
    }

    public Article(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public Article(String title, String body, User author, Hub hub) {
        this.title = title;
        this.body = body;
        this.author = author;
        this.hub = hub;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Hub getHub() {
        return hub;
    }

    public void setHub(Hub hub) {
        this.hub = hub;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
