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

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String userMainpage() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            // Invalid authentication or is not authenticated, so redirect to /logout .
            return "redirect:/logout";
        }

        Person person = this.getAuthenticatedPerson();

        if (person == null || person.getId() == null) {
            // Invalid authentication or is not authenticated, so redirect to /logout .
            return "redirect:/logout";
        }
        return "redirect:/persons/" + person.getId();
    }

    @RequestMapping(value = "/persons/{id}", method = RequestMethod.GET)
    public String userpage(Model model, @PathVariable Long id) {
        Person person = this.personRepository.findOne(id);
        model.addAttribute("person", person);
        return "person";
    }

    @RequestMapping(value = "/persons", method = RequestMethod.POST)
    public String newPerson(Model model,
            @Valid @ModelAttribute Person person,
            BindingResult bindingResult) {
        // Create a new Person.
        return this.personService.addUser(model, person, bindingResult);
    }

    private Person getAuthenticatedPerson() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        return this.personRepository.findByUsername(auth.getName());
    }
}
