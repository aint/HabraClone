package ua.sg.academy.java2.habraclone.webController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.sg.academy.java2.habraclone.dbModel.entity.Article;
import ua.sg.academy.java2.habraclone.service.ArticleService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ArticleController {

    private static final String ARTICLES_VIEW = "articles";
    private static final String ERROR_404 = "404";
    private static final String ARTICLE_ATTRIBUTE = "article";

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @RequestMapping(value = "/articles/{id}")
    public ModelAndView showArticleWithComments(@PathVariable("id") Long id) {
        Article article = (Article) articleService.getById(id);
        if (article == null) {
            return new ModelAndView(ERROR_404);
        }
        articleService.incrementViewsCount(article);
        return new ModelAndView(ARTICLES_VIEW, ARTICLE_ATTRIBUTE, article);
    }

    @RequestMapping(value = "/articles/{id}/add-favorite")
    public String addFavoriteArticle(@PathVariable("id") Long id, HttpServletRequest request) {
        articleService.addArticleToFavorites(getPrincipal(), id);

        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/");
//        return "redirect:" + "/" + ARTICLES_VIEW + "/" + id;
    }

    private String getPrincipal() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (principal instanceof UserDetails
                ? ((UserDetails)principal).getUsername()
                : principal.toString());
    }

}
