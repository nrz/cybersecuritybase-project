package codechat.controller;

import codechat.domain.Forum;
import codechat.domain.Message;
import codechat.domain.Person;
import codechat.repository.ForumRepository;
import codechat.repository.MessageRepository;
import codechat.repository.PersonRepository;
import codechat.service.ForumService;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Profile("default")
@Controller
public class DefaultController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ForumService forumService;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ForumRepository forumRepository;

    @Autowired
    private MessageRepository messageRepository;

    @PostConstruct
    public void init() {
        if (this.personRepository.findByUsername("user1") == null) {
            // Some test users.
            Person user1 = new Person();
            user1.setUsername("user1");
            user1.setPassword(this.passwordEncoder.encode("foo"));
            user1.setEmail("user1@example.com");
            user1.setUserRole("USER");
            this.personRepository.save(user1);

            Forum forum1 = new Forum();
            forum1.setName("Best words.");
            forum1.setTopic("Only best words. Big words. Great words. Words are best.");
            this.forumRepository.save(forum1);

            this.forumService.addMemberToForum(user1, forum1);

            Message message1 = new Message();
            message1.setText("Really?");
            message1.setPerson(user1);
            this.messageRepository.save(message1);

            this.forumService.addMessageToForum(message1, forum1);
        }

        if (this.personRepository.findByUsername("user2") == null) {
            Person user2 = new Person();
            user2.setUsername("user2");
            user2.setPassword(this.passwordEncoder.encode("bar"));
            user2.setEmail("user2@example.com");
            user2.setUserRole("USER");
            this.personRepository.save(user2);
        }
    }

    @RequestMapping("*")
    public String defaultMapping() {
        return "redirect:/main";
    }

    @RequestMapping(value = "/invalidlogin", method = RequestMethod.GET)
    public String invalidLogin() {
        // Login failed, display a new login form.
        return "loginform";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginForm() {
        return "redirect:/main";
    }
}
