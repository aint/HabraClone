package ua.sg.academy.java2.habraclone.webController.tag;

import ua.sg.academy.java2.habraclone.dbModel.dao.factory.HibernateDaoFactory;
import ua.sg.academy.java2.habraclone.dbModel.entity.Article;
import ua.sg.academy.java2.habraclone.dbModel.entity.User;
import ua.sg.academy.java2.habraclone.service.util.HibernateConnectionFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class LinkToLatestArticle extends TagSupport {

    private User user;

    @Override
    public int doStartTag() throws JspException {
        HibernateConnectionFactory.getSessionFactory().getCurrentSession().beginTransaction();
        Article article = HibernateDaoFactory.getArticleDao().getLatestArticleOfUser(user);
        HibernateConnectionFactory.getSessionFactory().getCurrentSession().getTransaction().commit();
        try {
            pageContext.getOut()
                    .print("<a href='articles/" + article.getId() + "'>" + article.getTitle() + "</a>");
            return SKIP_BODY;
        } catch (IOException e) {
            throw new JspException(e);
        }
    }

    public void setUser(User user) {
        this.user = user;
    }
}
