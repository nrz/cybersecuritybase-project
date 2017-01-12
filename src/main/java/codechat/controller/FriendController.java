package codechat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import codechat.domain.Friend;
import codechat.repository.FriendRepository;

public class FriendController {

    @Autowired
    private FriendRepository userRepository;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String user() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if (auth == null || !auth.isAuthenticated()) {
            // Invalid authentication or is not authenticated, so redirect to /main .
            return "redirect:/main";
        }

        Long id = 0L; // TODO: FIXME!
        return "redirect:/user/" + id;
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public String userpage() {
        return "user";
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String newUser(@RequestParam String name, @RequestParam String password) {
        this.userRepository.save(new Friend(name, password));
        return "done";
    }

    @RequestMapping(value = "/user/{id}/newfriend", method = RequestMethod.POST)
    public String newFriend(@RequestParam String name, @RequestParam String password) {
        this.userRepository.save(new Friend(name, password));
        return "done";
    }
}
