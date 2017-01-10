package sec.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ForumController {

    @RequestMapping(value = "/forum/{id}", method = RequestMethod.GET)
    public String getForum(@PathVariable Long id) {
        // Forum page.
        return "forum";
    }

    @RequestMapping(value = "/forum", method = RequestMethod.POST)
    public String addForum(String forumName) {
        // Create new forum.

        // TODO: Redirected to the newly created forum.
        return "";
    }

}
