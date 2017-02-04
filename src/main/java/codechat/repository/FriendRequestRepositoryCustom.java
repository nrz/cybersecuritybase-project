package codechat.repository;

import codechat.domain.FriendRequest;
import codechat.domain.Person;
import java.util.Collection;

public interface FriendRequestRepositoryCustom {

    public Collection<FriendRequest> findByUsernameTo(String username);

    public Collection<FriendRequest> findByPersonTo(Person person);
}
