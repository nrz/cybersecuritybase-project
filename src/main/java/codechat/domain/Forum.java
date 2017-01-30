package codechat.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Forum extends AbstractPersistable<Long> {

    // Each Forum may have a name.
    private String name;

    // Each Forum may have a topic.
    private String topic;

    // Each Forum must have at least one admin.
    @ManyToMany(mappedBy = "adminForums")
    private Collection<Person> forumAdmins;

    // Each Forum may have any number of non-admin members.
    @ManyToMany(mappedBy = "memberForums")
    private Collection<Person> forumMembers;

    @OneToMany
    private Collection<Message> messages;

    public Forum() {
        this("New forum");
    }

    public Forum(String name) {
        this.name = name;
        this.messages = new ArrayList<>();
    }

    public String getName() {
        if (this.name == null) {
            this.name = "";
        }
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Message> getMessages() {
        if (this.messages == null) {
            this.messages = new ArrayList<>();
        }
        return this.messages;
    }

    public void setMessages(Collection<Message> messages) {
        this.messages = messages;
    }

    public Collection<Person> getForumAdmins() {
        if (this.forumAdmins == null) {
            this.forumAdmins = new ArrayList<>();
        }
        return this.forumAdmins;
    }

    public void setForumAdmins(List<Person> forumAdmins) {
        this.forumAdmins = forumAdmins;
    }

    public void addForumAdmin(Person admin) {
        if (this.forumAdmins == null) {
            this.forumAdmins = new ArrayList<>();
        }
        this.forumAdmins.add(admin);
    }

    public Collection<Person> getForumMembers() {
        if (this.forumMembers == null) {
            this.forumMembers = new ArrayList<>();
        }
        return this.forumMembers;
    }

    public void setForumMembers(List<Person> forumMembers) {
        this.forumMembers = forumMembers;
    }

    public void addForumMember(Person member) {
        if (this.forumMembers == null) {
            this.forumMembers = new ArrayList<>();
        }
        this.forumMembers.add(member);
    }

    public String getTopic() {
        if (this.topic == null) {
            this.topic = "";
        }
        return this.topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
