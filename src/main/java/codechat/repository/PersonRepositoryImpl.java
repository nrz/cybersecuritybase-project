package codechat.repository;

import codechat.domain.Person;
import java.util.ArrayList;
import java.util.Collection;

public class PersonRepositoryImpl implements PersonRepositoryCustom {

    @Override
    public Collection<Person> findByPartialUsername(String partialUsername) {
        return new ArrayList<>();
    }
}
