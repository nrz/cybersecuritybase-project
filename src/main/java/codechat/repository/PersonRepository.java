package codechat.repository;

import codechat.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long>, PersonRepositoryCustom {

    Person findByUsername(String username);
}
