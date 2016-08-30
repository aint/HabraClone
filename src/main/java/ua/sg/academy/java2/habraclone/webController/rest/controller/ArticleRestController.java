package ua.sg.academy.java2.habraclone.webController.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.sg.academy.java2.habraclone.model.Article;
import ua.sg.academy.java2.habraclone.service.transactional.ArticleService;
import ua.sg.academy.java2.habraclone.webController.rest.json.ArticleJson;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api")
public class ArticleRestController {

    private final ArticleService articleService;

    @Autowired
    public ArticleRestController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @RequestMapping(value = "/articles/top")
    public Collection<ArticleJson> getTopArticles() {
        return articleService.getMostPopularArticles().stream()
                .map(ArticleJson::new)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/articles/{id}")
    public ResponseEntity<ArticleJson> getArticleById(@PathVariable("id") Long id) {
        Article article = (Article) articleService.getById(id);
        if (article == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        articleService.incrementViewsCount(article);
        return new ResponseEntity<>(new ArticleJson(article), HttpStatus.OK);
    }

}
