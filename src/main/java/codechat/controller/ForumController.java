package codechat.controller;

import codechat.domain.Forum;
import codechat.domain.Person;
import codechat.repository.ForumRepository;
import codechat.service.ForumService;
import codechat.service.PersonService;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ForumController {

    @Autowired
    private ForumService forumService;

    @Autowired
    private ForumRepository forumRepository;

    @Autowired
    private PersonService personService;

    @RequestMapping(value = "/forums", method = RequestMethod.GET)
    public String getForums(Model model) {
        Person person = this.personService.getAuthenticatedPerson();
        Collection<Forum> forums = this.forumRepository.findByPerson(person);
        model.addAttribute("forums", forums);
        return "myforums";
    }

    @RequestMapping(value = "/forums/{id}", method = RequestMethod.GET)
    public String getForum(Model model, @PathVariable Long id) {
        Forum forum = this.forumRepository.findOne(id);
        model.addAttribute("forum", forum);
        return "forum";
    }

    @RequestMapping(value = "/forums", method = RequestMethod.POST)
    public String addForum(Model model,
            @ModelAttribute Forum forum,
            BindingResult bindingResult) {
        return this.forumService.createForum(model, forum, bindingResult);
    }
}
