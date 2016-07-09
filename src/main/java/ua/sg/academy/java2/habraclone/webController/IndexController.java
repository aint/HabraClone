package ua.sg.academy.java2.habraclone.webController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.sg.academy.java2.habraclone.service.ArticleService;

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
        return new ModelAndView(INDEX_VIEW, ARTICLES_ATTRIBUTE, articleService.getAll());
    }

}
