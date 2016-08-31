package ua.sg.academy.java2.habraclone.webController.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.sg.academy.java2.habraclone.model.User;
import ua.sg.academy.java2.habraclone.service.transactional.UserService;
import ua.sg.academy.java2.habraclone.webController.rest.json.ArticleJson;
import ua.sg.academy.java2.habraclone.webController.rest.json.CommentJson;
import ua.sg.academy.java2.habraclone.webController.rest.json.UserJson;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api")
public class UserRestController {

    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/users")
    public Collection<UserJson> getAllUsersSortedByRating() {
        return userService.getAllSortedDeskByRating().stream()
                .map(user -> new UserJson((User) user))
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/users/{username}")
    public ResponseEntity<UserJson> getUserByUsername(@PathVariable("username") String username) {
        User user = userService.getByUserName(username);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new UserJson(user), HttpStatus.OK);
    }

    @RequestMapping(value = "/users/{username}/articles")
    public Collection<ArticleJson> userArticles(@PathVariable("username") String username) {
        return userService.getByUserName(username).getArticles().stream()
                .map(ArticleJson::new)
                .collect(Collectors.toList());

    }

    @RequestMapping(value = "/users/{username}/favorites")
    public Collection<ArticleJson> userFavorites(@PathVariable("username") String username) {
        return userService.getByUserName(username).getFavorites().stream()
                .map(ArticleJson::new)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/users/{username}/comments")
    public Collection<CommentJson> userComments(@PathVariable("username") String username) {
        return userService.getByUserName(username).getComments().stream()
                .map(CommentJson::new)
                .collect(Collectors.toList());
    }


}
