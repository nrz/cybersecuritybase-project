package codechat.repository;

import codechat.domain.Forum;
import codechat.domain.Person;
import java.util.Collection;

public interface ForumRepositoryCustom {

    public Collection<Forum> findByUsername(String username);

    public Collection<Forum> findByPerson(Person person);
}
