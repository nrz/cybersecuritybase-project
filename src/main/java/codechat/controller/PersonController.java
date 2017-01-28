package codechat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import codechat.domain.Person;
import codechat.repository.PersonRepository;
import org.springframework.web.bind.annotation.PathVariable;

public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @RequestMapping(value = "/person", method = RequestMethod.GET)
    public String user() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            // Invalid authentication or is not authenticated, so redirect to /main .
            return "redirect:/main";
        }

        Long id = 0L; // TODO: FIXME!
        return "redirect:/user/" + id;
    }

    @RequestMapping(value = "/person/{id}", method = RequestMethod.GET)
    public String userpage() {
        return "user";
    }

    @RequestMapping(value = "/person", method = RequestMethod.POST)
    public String newPerson(@RequestParam String name, @RequestParam String password) {
        this.personRepository.save(new Person(name, password));
        return "done";
    }
}
