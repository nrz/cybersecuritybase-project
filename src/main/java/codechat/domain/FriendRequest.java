package codechat.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class FriendRequest extends AbstractPersistable<Long> {

    @ManyToOne
    private Person personFrom;

    @ManyToOne
    private Person personTo;

    private String message;

    public FriendRequest() {

    }

    public Person getPersonFrom() {
        return this.personFrom;
    }

    public void setPersonFrom(Person personFrom) {
        this.personFrom = personFrom;
    }

    public Person getTo() {
        return this.personTo;
    }

    public void setTo(Person to) {
        this.personTo = to;
    }

    public String getMessage() {
        if (this.message == null) {
            this.message = "";
        }
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
