package codechat.repository;

import codechat.domain.FriendRequest;
import codechat.domain.Person;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

public class FriendRequestRepositoryImpl implements FriendRequestRepositoryCustom {

    @Autowired
    private FriendRequestRepository friendRequestRepository;

    @Autowired
    private PersonRepository personRepository;

    @Override
    public Collection<FriendRequest> findByPersonTo(Person person) {
        if (person == null) {
            return new ArrayList<>();
        }

        return this.friendRequestRepository.findAll().stream().filter(friendRequest -> friendRequest.getTo().equals(person)).collect(Collectors.toList());
    }

    @Override
    public Collection<FriendRequest> findByUsernameTo(String username) {
        Person person = this.personRepository.findByUsername(username);
        return this.findByPersonTo(person);
    }
}
