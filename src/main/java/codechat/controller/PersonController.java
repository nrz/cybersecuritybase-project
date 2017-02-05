package codechat.controller;

import codechat.domain.Person;
import codechat.repository.ForumRepository;
import codechat.repository.FriendRequestRepository;
import codechat.repository.PersonRepository;
import codechat.service.PersonService;
import java.util.Collection;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PersonController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository personRepository;

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

            if (authenticatedPerson.getUserRole().contains("ADMIN")) {
                model.addAttribute("admin", person);
            }
        }
        return "main";
    }

    @RequestMapping(value = "/persons/{id}", method = RequestMethod.GET)
    public String friendPage(Model model, @PathVariable Long id) {
        if (this.personService.getAuthenticatedPerson() == null) {
            return "redirect:/main";
        }

        Person friend = this.personRepository.findOne(id);
        if (friend == null) {
            // No such person!
            return "redirect:/main";
        }

        Person authenticatedPerson = this.personService.getAuthenticatedPerson();

        model.addAttribute("loggedInUser", authenticatedPerson.getUsername());
        model.addAttribute("email", authenticatedPerson.getEmail());
        model.addAttribute("loggedInMessage", "You are logged in as " + authenticatedPerson.getUsername());
        model.addAttribute("friend", friend);
        return "main";
    }

    @RequestMapping(value = "/admin/persons/{id}/newpassword", method = RequestMethod.GET)
    public String changePasswordPage(Model model,
            @PathVariable Long id) {
        Person authenticatedPerson = this.personService.getAuthenticatedPerson();
        if (authenticatedPerson == null) {
            return "redirect:/main";
        }

        Person user = this.personRepository.findOne(id);
        if (user == null) {
            // No such person!
            return "redirect:/main";
        }

        model.addAttribute("admin", authenticatedPerson);
        model.addAttribute("user", user);
        return "changepassword";
    }

    @RequestMapping(value = "/admin/persons/{id}/newpassword", method = RequestMethod.POST)
    public String changePassword(@Valid @ModelAttribute("newPassword") String newPassword,
            @PathVariable Long id) {

        if (newPassword == null || newPassword.isEmpty()) {
            // Cannot set a null or empty password!
            return "redirect:/main";
        }

        if (this.personService.getAuthenticatedPerson() == null) {
            return "redirect:/main";
        }

        Person person = this.personRepository.findOne(id);
        if (person == null) {
            // No such person!
            return "redirect:/main";
        }

        person.setPassword(this.passwordEncoder.encode(newPassword));
        this.personRepository.save(person);
        return "redirect:/main";
    }

    @RequestMapping(value = "/main", method = RequestMethod.POST)
    public String newPerson(Model model,
            @Valid @ModelAttribute Person person,
            BindingResult bindingResult) {
        // Create a new Person.
        return this.personService.addPerson(model, person, bindingResult);
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPage(Model model,
            @ModelAttribute Person person
    ) {
        // Show user information on admin page.
        if (this.personService.getAuthenticatedPerson() == null) {
            return "redirect:/main";
        }

        Person authenticatedPerson = this.personService.getAuthenticatedPerson();
        model.addAttribute("loggedInUser", authenticatedPerson.getUsername());
        model.addAttribute("admin", authenticatedPerson.getUsername());
        model.addAttribute("email", authenticatedPerson.getEmail());
        model.addAttribute("loggedInMessage", "You are logged in as " + authenticatedPerson.getUsername());
        model.addAttribute("isOnAdminPage", true);

        Collection<Person> users = this.personRepository.findAll();
        model.addAttribute("users", users);
        return "main";
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public String cancelAction(Model model) {
        return "redirect:/main";
    }
}
