package codechat.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class FriendRequest extends AbstractPersistable<Long> {

    @ManyToOne
    private Person from;

    @ManyToOne
    private Person to;
}
