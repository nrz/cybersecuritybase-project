package codechat.service;

import codechat.domain.FriendRequest;
import codechat.repository.FriendRequestRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Service
public class FriendRequestService {

    @Autowired
    private FriendRequestRepository friendRequestRepository;

    public String createFriendRequest(Model model, FriendRequest friendRequest, BindingResult bindingResult) {
        boolean hasErrors = false;

        List<String> errors = new ArrayList<>();

        if (friendRequest == null || bindingResult == null || bindingResult.hasErrors()) {
            errors.add("Error in friend request creation.");
            hasErrors = true;
        }

        if (hasErrors) {
            model.addAttribute("errors", errors);
            return "redirect:/main";
        }

        this.friendRequestRepository.save(friendRequest);

        // TODO: Redirect to the newly created friend request.
        return "redirect:/main";
    }
}
