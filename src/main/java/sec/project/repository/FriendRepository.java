package sec.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sec.project.domain.Friend;

public interface FriendRepository extends JpaRepository<Friend, Long> {

}
