package codechat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import codechat.domain.Person;
import codechat.repository.PersonRepository;
import codechat.service.PersonService;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PersonController {

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository personRepository;

    @RequestMapping(value = "/persons", method = RequestMethod.GET)
    public String user() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            // Invalid authentication or is not authenticated, so redirect to /main .
            return "redirect:/main";
        }

        Person person = this.getAuthenticatedPerson();

        if (person == null || person.getId() == null) {
            // Invalid authentication or is not authenticated, so redirect to /main .
            return "redirect:/main";
        }
        return "redirect:/persons/" + person.getId();
    }

    @RequestMapping(value = "/persons/{id}", method = RequestMethod.GET)
    public String userpage(@PathVariable Long id) {
        this.personRepository.findOne(id);
        return "user";
    }

    @RequestMapping(value = "/persons", method = RequestMethod.POST)
    public String newPerson(Model model,
            @Valid @ModelAttribute Person person,
            BindingResult bindingResult,
            @RequestParam String newUsername,
            @RequestParam String newPassword) {
        // Create a new Person.
        return this.personService.addUser(model, person, bindingResult, newUsername, newPassword);
    }

    private Person getAuthenticatedPerson() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        return this.personRepository.findByUsername(auth.getName());
    }
}
