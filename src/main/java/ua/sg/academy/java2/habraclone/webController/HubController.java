package ua.sg.academy.java2.habraclone.webController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.sg.academy.java2.habraclone.service.HubService;

@Controller
public class HubController {

    private static final String HUBS_VIEW = "hubs";
    private static final String INDEX_VIEW = "index";
    private static final String HUBS_ATTRIBUTE = "HUBS";
    private static final String ARTICLES_ATTRIBUTE = "ARTICLES";

    private final HubService hubService;

    @Autowired
    public HubController(HubService hubService) {
        this.hubService = hubService;
    }

    @RequestMapping(value = "/hubs")
    public ModelAndView showHubs() {
        return new ModelAndView(HUBS_VIEW, HUBS_ATTRIBUTE, hubService.getAllSortedDeskByRating());
    }

    @RequestMapping(value = "/hubs/{id}/articles")
    public ModelAndView showHubArticles(@PathVariable Long id) {
        return new ModelAndView(INDEX_VIEW, ARTICLES_ATTRIBUTE, hubService.getAllArticlesOfHub(id));
    }

}
