package codechat.domain;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Message extends AbstractPersistable<Long> {

    private String text;

    @ManyToOne
    private Person person;

    @ManyToOne
    private Forum forum;

    @Temporal(TemporalType.TIMESTAMP)
    private Date messageTimestamp;

    public Message() {
        this.messageTimestamp = new Date();
    }

    public String getText() {
        if (this.text == null) {
            this.text = "";
        }
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Person getPerson() {
        return this.person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Forum getForum() {
        return this.forum;
    }

    public void setForum(Forum forum) {
        this.forum = forum;
    }

    public Date getMessageTimestamp() {
        return this.messageTimestamp;
    }

    public String getTimeString() {
        if (this.messageTimestamp == null) {
            return "";
        }
        return this.messageTimestamp.toString();
    }

    public void setMessageTimestamp(Date messageTimestamp) {
        this.messageTimestamp = messageTimestamp;
    }
}
