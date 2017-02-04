package codechat.controller;

import codechat.domain.FriendRequest;
import codechat.domain.Person;
import codechat.repository.FriendRequestRepository;
import codechat.service.FriendRequestService;
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
public class FriendRequestController {

    @Autowired
    private PersonService personService;

    @Autowired
    private FriendRequestService friendRequestService;

    @Autowired
    private FriendRequestRepository friendRequestRepository;

    @RequestMapping(value = "/friendrequests", method = RequestMethod.GET)
    public String getFriendRequest(Model model) {
        Person person = this.personService.getAuthenticatedPerson();
        Collection<FriendRequest> friendRequests = this.friendRequestRepository.findByPersonTo(person);
        model.addAttribute("friendrequests", friendRequests);
        return "friendrequests";
    }

    @RequestMapping(value = "/friendrequests/{id}", method = RequestMethod.GET)
    public String getFriendRequest(Model model, @PathVariable Long id) {
        FriendRequest friendRequest = this.friendRequestRepository.findOne(id);
        model.addAttribute("friendrequest", friendRequest);
        return "friendrequests";
    }

    @RequestMapping(value = "/friendrequests", method = RequestMethod.POST)
    public String addFriendRequest(Model model,
            @ModelAttribute("personTo") String personToString,
            @ModelAttribute("message") String message,
            BindingResult bindingResult) {
        return this.friendRequestService.createFriendRequest(model, personToString, message, bindingResult);
    }
}
