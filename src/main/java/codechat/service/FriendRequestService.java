package codechat.service;

import codechat.domain.FriendRequest;
import codechat.domain.Person;
import codechat.repository.FriendRequestRepository;
import codechat.repository.PersonRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Service
public class FriendRequestService {

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private FriendRequestRepository friendRequestRepository;

    public String createFriendRequest(Model model, String personToString, String message, BindingResult bindingResult) {
        boolean hasErrors = false;

        List<String> errors = new ArrayList<>();

        if (personToString == null || personToString.isEmpty() || bindingResult == null || bindingResult.hasErrors()) {
            errors.add("Error in friend request creation.");
            hasErrors = true;
        }

        if (hasErrors) {
            model.addAttribute("errors", errors);
            return "redirect:/main";
        }

        Person personFrom = this.personService.getAuthenticatedPerson();

        if (personFrom == null) {
            errors.add("Error: null authentication.");
            model.addAttribute("errors", errors);
            return "redirect:/main";
        }

        Person personTo = this.personRepository.findByUsername(personToString);

        if (personTo == null) {
            errors.add("Error: no such person.");
            model.addAttribute("errors", errors);
            return "redirect:/main";
        }

        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setPersonTo(personTo);
        friendRequest.setPersonFrom(personFrom);

        if (message == null) {
            friendRequest.setMessage("");
        } else {
            friendRequest.setMessage(message);
        }

        this.friendRequestRepository.save(friendRequest);

        // TODO: Redirect to the newly created friend request.
        return "redirect:/main";
    }

    public String acceptFriendRequest(Long id) {
        Person person = this.personService.getAuthenticatedPerson();
        Collection<FriendRequest> friendRequests = this.friendRequestRepository.findByPersonTo(person);
        FriendRequest friendRequest = this.friendRequestRepository.findOne(id);

        if (friendRequest == null) {
            // null friend request!
            return "redirect:/main";
        }

        Person mePerson = friendRequest.getPersonTo();

        if (!friendRequests.contains(friendRequest) || mePerson != friendRequest.getPersonTo()) {
            // Not authorized to access this friend request!
            return "redirect:/main";
        }

        // Accept the friend request.
        Person otherPerson = friendRequest.getPersonFrom();
        mePerson.addFriend(otherPerson);
        otherPerson.addFriend(mePerson);
        this.personRepository.save(mePerson);
        this.personRepository.save(otherPerson);

        // Delete the friend request.
        this.friendRequestRepository.delete(id);

        return "redirect:/main";
    }

    public String deleteFriendRequest(Long id) {
        Person person = this.personService.getAuthenticatedPerson();
        Collection<FriendRequest> friendRequests = this.friendRequestRepository.findByPersonTo(person);
        FriendRequest friendRequest = this.friendRequestRepository.findOne(id);

        if (friendRequest == null) {
            // null friend request!
            return "redirect:/main";
        }

        Person mePerson = friendRequest.getPersonTo();

        if (!friendRequests.contains(friendRequest) || mePerson != friendRequest.getPersonTo()) {
            // Not authorized to access this friend request!
            return "redirect:/main";
        }

        // Delete the friend request.
        this.friendRequestRepository.delete(id);

        return "redirect:/main";
    }
}
