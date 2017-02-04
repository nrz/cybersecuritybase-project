package codechat.controller;

import codechat.repository.FriendRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class FriendRequestController {

    @Autowired
    private FriendRequestRepository friendRequestRepository;
}
