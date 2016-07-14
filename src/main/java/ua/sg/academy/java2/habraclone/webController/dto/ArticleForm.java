package ua.sg.academy.java2.habraclone.webController.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ArticleForm {

    @NotNull
    private Long hubId;
    @NotNull
    @Size(min = 5, max = 80, message = "{add_article.error.title}")
    private String title;
    @NotNull
    @Size(min = 50, message = "{add_article.error.preview}")
    private String preview;
    @NotNull
    @Size(min = 100, message = "{add_article.error.body}")
    private String body;
    @NotNull
    @Size(min = 3, message = "{add_article.error.keywords}")
    private String keywords;

    public Long getHubId() {
        return hubId;
    }

    public void setHubId(Long hubId) {
        this.hubId = hubId;
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

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

}
