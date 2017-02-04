package codechat.controller;

import codechat.domain.Person;
import codechat.repository.ForumRepository;
import codechat.repository.FriendRequestRepository;
import codechat.service.PersonService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PersonController {

    @Autowired
    private PersonService personService;

    @Autowired
    private FriendRequestRepository friendRequestRepository;

    @Autowired
    private ForumRepository forumRepository;

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String mainPage(Model model, @ModelAttribute Person person) {
        if (this.personService.getAuthenticatedPerson() != null) {
            Person authenticatedPerson = this.personService.getAuthenticatedPerson();
            model.addAttribute("loggedInUser", authenticatedPerson.getUsername());
            model.addAttribute("email", authenticatedPerson.getEmail());
            model.addAttribute("loggedInMessage", "You are logged in as " + authenticatedPerson.getUsername());
            model.addAttribute("friends", authenticatedPerson.getMyFriends());
            model.addAttribute("friendrequests", this.friendRequestRepository.findByPersonTo(authenticatedPerson));
            model.addAttribute("forums", this.forumRepository.findByPerson(authenticatedPerson));
        }
        return "main";
    }

    @RequestMapping(value = "/main", method = RequestMethod.POST)
    public String newPerson(Model model,
            @Valid @ModelAttribute Person person,
            BindingResult bindingResult) {
        // Create a new Person.
        return this.personService.addPerson(model, person, bindingResult);
    }
}
