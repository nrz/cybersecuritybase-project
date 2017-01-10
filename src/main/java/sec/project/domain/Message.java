package sec.project.domain;

import javax.persistence.Entity;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Message extends AbstractPersistable<Long> {

    private String messageText;

    public Message() {
        this.messageText = "";
    }

    public String getMessageText() {
        if (this.messageText == null) {
            this.messageText = "";
        }
        return this.messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
}
