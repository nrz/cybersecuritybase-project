package codechat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import codechat.domain.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
