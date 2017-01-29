package codechat.repository;

import codechat.domain.Forum;
import java.util.Collection;

public interface ForumRepositoryCustom {

    public Collection<Forum> FindByUsername(String username);
}
