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

    private String message;

    public FriendRequest() {

    }

    public Person getFrom() {
        return this.from;
    }

    public void setFrom(Person from) {
        this.from = from;
    }

    public Person getTo() {
        return this.to;
    }

    public void setTo(Person to) {
        this.to = to;
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
