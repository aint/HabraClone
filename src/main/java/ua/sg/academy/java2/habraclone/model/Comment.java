package ua.sg.academy.java2.habraclone.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "Comment")
@NamedQueries({
        @NamedQuery(name = "getAllCommentsOfUser", query = "FROM Comment c WHERE c.author = :author"),
        @NamedQuery(name = "getLatestCommentOfUser", query = "FROM Comment com WHERE com.creationDate = (SELECT MAX(c.creationDate) FROM Comment c WHERE c.author = :author)"),
})
public class Comment implements IEntity {
    private static final long serialVersionUID = -2509780003331344119L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(columnDefinition = "text", nullable = false)
    private String body;
    @Column
    private int rating;
    @Column(nullable = false)
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
