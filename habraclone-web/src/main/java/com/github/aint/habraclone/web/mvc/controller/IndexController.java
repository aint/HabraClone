package com.github.aint.habraclone.web.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;
import com.github.aint.habraclone.service.transactional.inter.ArticleService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class IndexController {

    private static final String INDEX_VIEW = "index";
    private static final String ARTICLES_ATTRIBUTE = "ARTICLES";

    private final ArticleService articleService;

    @Autowired
    public IndexController(ArticleService articleService) {
        this.articleService = articleService;
    }


    @RequestMapping(value = "/")
    public ModelAndView index() {
        return new ModelAndView(INDEX_VIEW, ARTICLES_ATTRIBUTE, articleService.getAllSortedAscByDate());
    }


    @RequestMapping(value = "/locale/{ln}**")
    public String changeLocale(@PathVariable("ln") String ln, @RequestParam String url,
                               HttpServletRequest request, HttpServletResponse response) {
//        Locale locale = LocaleContextHolder.getLocale();
        LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
        localeResolver.setLocale(request, response, StringUtils.parseLocaleString(ln));
        return "redirect:" + (url != null ? url : "/");
    }

}
