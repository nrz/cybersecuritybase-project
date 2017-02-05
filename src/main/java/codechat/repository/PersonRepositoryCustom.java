package codechat.repository;

import codechat.domain.Person;
import java.util.Collection;

public interface PersonRepositoryCustom {

    public Collection<Person> findByPartialUsername(String partialUsername);
}
