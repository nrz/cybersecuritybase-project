package codechat.service;

import codechat.domain.FriendRequest;
import codechat.domain.Person;
import codechat.repository.FriendRequestRepository;
import codechat.repository.PersonRepository;
import java.util.ArrayList;
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

    public String createFriendRequest(Model model, String personToString, BindingResult bindingResult) {
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

        this.friendRequestRepository.save(friendRequest);

        // TODO: Redirect to the newly created friend request.
        return "redirect:/main";
    }
}
