package codechat.repository;

import codechat.domain.Forum;
import codechat.domain.Person;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

public class ForumRepositoryImpl implements ForumRepositoryCustom {

    @Autowired
    private ForumRepository forumRepository;

    @Autowired
    private PersonRepository personRepository;

    @Override
    public Collection<Forum> findByUsername(String username) {
        Person person = this.personRepository.findByUsername(username);
        if (person == null) {
            return new ArrayList<>();
        }

        return this.forumRepository.findAll().stream().filter(forum -> forum.getForumMembers().contains(person)).collect(Collectors.toList());
    }
}
