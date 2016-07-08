package ua.sg.academy.java2.habraclone.webController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.sg.academy.java2.habraclone.dbModel.entity.Article;
import ua.sg.academy.java2.habraclone.service.ArticleService;

@Controller
public class ArticleController {

    private static final String ARTICLES_VIEW = "articles";
    private static final String ERROR_404 = "404";
    private static final String ARTICLE_ATTRIBUTE = "article";
    private static final String COMMENTS_ATTRIBUTE = "comments";

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @RequestMapping(value = "/articles/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        Article article = articleService.getArticleById(id);
        if (article == null) {
            return ERROR_404;
        }
        model.addAttribute(COMMENTS_ATTRIBUTE, articleService.getCommentsOfArticle(article));
        model.addAttribute(ARTICLE_ATTRIBUTE, article);
        return ARTICLES_VIEW;
    }


}
