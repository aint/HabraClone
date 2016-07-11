package ua.sg.academy.java2.habraclone.webController.tag;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.tags.RequestContextAwareTag;
import ua.sg.academy.java2.habraclone.dbModel.dao.ArticleDao;
import ua.sg.academy.java2.habraclone.dbModel.entity.Article;
import ua.sg.academy.java2.habraclone.dbModel.entity.User;

import javax.servlet.jsp.JspException;
import java.io.IOException;

@Component
public class LinkToLatestArticle extends RequestContextAwareTag {

    private User user;

    @Override
    public int doStartTagInternal() throws JspException {
        ArticleDao articleDao = getRequestContext().getWebApplicationContext().getBean(ArticleDao.class);
        Article article = articleDao.getLatestArticleOfUser(user);
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
