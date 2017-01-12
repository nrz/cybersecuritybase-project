package codechat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import codechat.domain.Friend;

public interface FriendRepository extends JpaRepository<Friend, Long> {

}
