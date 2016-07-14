package ua.sg.academy.java2.habraclone.webController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.sg.academy.java2.habraclone.dbModel.entity.Article;
import ua.sg.academy.java2.habraclone.service.ArticleService;
import ua.sg.academy.java2.habraclone.service.HubService;
import ua.sg.academy.java2.habraclone.webController.dto.ArticleForm;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class ArticleController {

    private static final String INDEX_VIEW = "index";
    private static final String ARTICLES_VIEW = "articles";
    private static final String ADD_ARTICLE_VIEW = "addArticle";
    private static final String ERROR_404 = "404";
    private static final String ARTICLE_ATTRIBUTE = "article";
    private static final String HUBS_ATTRIBUTE = "HUBS";
    private static final String TOP_ARTICLES_ATTRIBUTE = "TOP_ARTICLES";

    private final ArticleService articleService;
    private final HubService hubService;

    @Autowired
    public ArticleController(ArticleService articleService, HubService hubService) {
        this.articleService = articleService;
        this.hubService = hubService;
    }

    @RequestMapping(value = "/articles/top")
    public ModelAndView showTopArticles() {
        return new ModelAndView(INDEX_VIEW, TOP_ARTICLES_ATTRIBUTE, articleService.getMostPopularArticles());
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

    @RequestMapping(value = "/articles/add", method = RequestMethod.GET)
    public ModelAndView addArticle() {
        return new ModelAndView(ADD_ARTICLE_VIEW, HUBS_ATTRIBUTE, hubService.getAll());
    }

    @RequestMapping(value = "/articles/add", method = RequestMethod.POST)
    public ModelAndView addArticle(@ModelAttribute("articleForm") @Valid ArticleForm articleForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView(ADD_ARTICLE_VIEW, HUBS_ATTRIBUTE, hubService.getAll());
        }
        Long id = articleService.createAndSave(articleForm, getPrincipal());
        return new ModelAndView("redirect:/articles/" + id);
    }

    private String getPrincipal() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (principal instanceof UserDetails
                ? ((UserDetails)principal).getUsername()
                : principal.toString());
    }

}
