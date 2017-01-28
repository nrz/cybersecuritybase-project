package codechat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import codechat.domain.Person;
import codechat.repository.PersonRepository;
import javax.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Profile("default")
@Controller
public class DefaultController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PersonRepository personRepository;

    @PostConstruct
    public void init() {
        if (this.personRepository.findByUsername("user1") == null) {
            // Some test users.
            Person user1 = new Person();
            user1.setUsername("user1");
            user1.setPassword(this.passwordEncoder.encode("foo"));
            this.personRepository.save(user1);
        }

        if (this.personRepository.findByUsername("user2") == null) {
            Person user2 = new Person();
            user2.setUsername("user2");
            user2.setPassword(this.passwordEncoder.encode("bar"));
            this.personRepository.save(user2);
        }
    }

    @RequestMapping("*")
    public String defaultMapping() {
        return "redirect:/persons";
    }

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String loadForm() {
        return "form";
    }

    @RequestMapping(value = "/invalidlogin", method = RequestMethod.GET)
    public String invalidLogin() {
        // Login failed, display a new login form.
        return "loginform";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginForm() {
        return "form";
    }
}
