package com.github.aint.habraclone.web.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.github.aint.habraclone.data.model.Article;
import com.github.aint.habraclone.service.transactional.inter.ArticleService;
import com.github.aint.habraclone.service.transactional.inter.HubService;
import com.github.aint.habraclone.service.dto.ArticleForm;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.github.aint.habraclone.web.mvc.util.ConstantsHolder.ERROR_404;

@Controller
public class ArticleController {

    private static final String INDEX_VIEW = "index";
    private static final String ARTICLES_VIEW = "articles";
    private static final String ADD_ARTICLE_VIEW = "addArticle";
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
    public String addArticle(Model model) {
        model.addAttribute(HUBS_ATTRIBUTE, hubService.getAll());
        model.addAttribute("articleForm", new ArticleForm());
        return ADD_ARTICLE_VIEW;
    }

    @RequestMapping(value = "/articles/add", method = RequestMethod.POST)
    public ModelAndView addArticle(@ModelAttribute("articleForm") @Valid ArticleForm articleForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView(ADD_ARTICLE_VIEW, HUBS_ATTRIBUTE, hubService.getAll());
        }
        Long id = articleService.createAndSave(articleForm, getPrincipal());
        return new ModelAndView("redirect:/articles/" + id);
    }

    @RequestMapping(value = "/articles/{id}/vote/plus")
    public String votePlusForArticle(@PathVariable Long id) {
        articleService.voteForArticle(id, getPrincipal(), true);
        return "redirect:/articles/" + id;
    }

    @RequestMapping(value = "/articles/{id}/vote/minus")
    public String voteMinusForArticle(@PathVariable Long id) {
        articleService.voteForArticle(id, getPrincipal(), false);
        return "redirect:/articles/" + id;
    }

    private String getPrincipal() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (principal instanceof UserDetails
                ? ((UserDetails)principal).getUsername()
                : principal.toString());
    }

}