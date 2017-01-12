package codechat.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Forum extends AbstractPersistable<Long> {

    // Each Forum must have a name.
    @NotBlank(message = "Give a name for the forum")
    private String name;

    // Each Forum may have a topic.
    private String topic;

    // Each Forum must have at least one admin.
    @ManyToMany(mappedBy = "adminForums")
    private Collection<Friend> forumAdmins;

    // Each Forum may have any number of non-admin members.
    @ManyToMany(mappedBy = "memberForums")
    private Collection<Friend> forumMembers;

    @OneToMany
    private List<Message> messages;

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

    public List<Message> getMessages() {
        if (this.messages == null) {
            this.messages = new ArrayList<>();
        }
        return this.messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Collection<Friend> getForumAdmins() {
        if (this.forumAdmins == null) {
            this.forumAdmins = new ArrayList<>();
        }
        return this.forumAdmins;
    }

    public void setForumAdmins(List<Friend> forumAdmins) {
        this.forumAdmins = forumAdmins;
    }

    public void addForumAdmin(Friend admin) {
        this.forumMembers.add(admin);
    }

    public Collection<Friend> getForumMembers() {
        if (this.forumMembers == null) {
            this.forumMembers = new ArrayList<>();
        }
        return this.forumMembers;
    }

    public void setForumMembers(List<Friend> forumMembers) {
        this.forumMembers = forumMembers;
    }

    public void addForumMember(Friend member) {
        this.forumMembers.add(member);
    }
}
