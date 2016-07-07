package ua.sg.academy.java2.habraclone.webController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

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
    public String index(Map<String, Object> model) {
        model.put(ARTICLES_ATTRIBUTE, articleService.getAllArticles());
        return INDEX_VIEW;
    }

}
