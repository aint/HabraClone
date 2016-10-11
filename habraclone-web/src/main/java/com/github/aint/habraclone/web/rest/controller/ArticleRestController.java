package com.github.aint.habraclone.web.rest.controller;

import com.github.aint.habraclone.web.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.github.aint.habraclone.data.model.Article;
import com.github.aint.habraclone.service.transactional.ArticleService;
import com.github.aint.habraclone.web.rest.json.ArticleJson;
import com.github.aint.habraclone.web.rest.json.CommentJson;

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
        Article article = articleService.getById(id).orElseThrow(ResourceNotFoundException::new);
        articleService.incrementViewsCount(article);
        return new ResponseEntity<>(new ArticleJson(article), HttpStatus.OK);
    }

    @RequestMapping(value = "/articles/{id}/comments")
    public ResponseEntity<Collection<CommentJson>> getCommentOfArticle(@PathVariable("id") Long id) {
        Article article = articleService.getById(id).orElseThrow(ResourceNotFoundException::new);
        return new ResponseEntity<>(
                article.getComments().stream()
                        .map(CommentJson::new)
                        .collect(Collectors.toList()), HttpStatus.OK);
    }

}
