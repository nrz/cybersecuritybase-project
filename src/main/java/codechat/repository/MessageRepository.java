package codechat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import codechat.domain.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
