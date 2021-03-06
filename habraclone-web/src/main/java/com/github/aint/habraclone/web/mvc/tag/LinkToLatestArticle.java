package com.github.aint.habraclone.web.mvc.tag;

import com.github.aint.habraclone.data.model.Article;
import com.github.aint.habraclone.data.model.User;
import com.github.aint.habraclone.service.transactional.ArticleService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import javax.servlet.jsp.JspException;
import java.io.IOException;
import java.util.NoSuchElementException;

@Component
public class LinkToLatestArticle extends RequestContextAwareTag {

    private User user;

    @Override
    public int doStartTagInternal() throws JspException {
        ArticleService articleService = getRequestContext().getWebApplicationContext().getBean(ArticleService.class);
        Article article = articleService.getLatestArticleOfUser(user)
                .orElseThrow(() -> new NoSuchElementException("User haven't any article"));
        try {
            pageContext.getOut().print("<a href='/articles/" + article.getId() + "'>" + article.getTitle() + "</a>");
            return SKIP_BODY;
        } catch (IOException e) {
            throw new JspException(e);
        }
    }

    public void setUser(User user) {
        this.user = user;
    }
}
