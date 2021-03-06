package codechat.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Person extends AbstractPersistable<Long> {

    @NotBlank(message = "Give a username")
    private String username;

    @NotBlank(message = "Give a password")
    private String password;

    private String unencryptedPassword;

    private String email;

    private String userRole;

    // Each Person may be an admin in many Forums and each Forum may have many admins.
    @ManyToMany
    private Collection<Forum> adminForums;

    // Each Person may belong to many Forums and each Forum may have many Persons.
    @ManyToMany
    private Collection<Forum> memberForums;

    @OneToMany
    private Collection<Message> messages;

    // Each Person may belong to many FriendGroups, that is, s/he can be
    // a friend for many other Persons.
    @ManyToMany(mappedBy = "friendsTo", fetch = FetchType.EAGER)
    private Set<Person> myFriends;

    @ManyToMany
    private Set<Person> friendsTo;

    public Person() {
        this("", null);
    }

    public Person(String username, String password) {
        this.username = username;
        this.password = password;
        this.adminForums = new ArrayList<>();
        this.memberForums = new ArrayList<>();
        this.myFriends = new HashSet<>();
        this.friendsTo = new HashSet<>();
    }

    public String getUsername() {
        if (this.username == null) {
            this.username = "";
        }
        return this.username;
    }

    public String getEmail() {
        if (this.email == null) {
            this.email = "";
        }
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.username);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Person other = (Person) obj;
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Forum> getAdminForums() {
        if (this.adminForums == null) {
            this.adminForums = new ArrayList<>();
        }
        return this.adminForums;
    }

    public void setAdminForums(List<Forum> forums) {
        this.adminForums = forums;
    }

    public void addAdminForum(Forum forum) {
        this.adminForums.add(forum);
    }

    public Collection<Forum> getMemberForums() {
        if (this.memberForums == null) {
            this.memberForums = new ArrayList<>();
        }
        return this.memberForums;
    }

    public void setMemberForums(List<Forum> forums) {
        this.memberForums = forums;
    }

    public void addMemberForum(Forum forum) {
        this.memberForums.add(forum);
    }

    public Collection<Person> getMyFriends() {
        if (this.myFriends == null) {
            this.myFriends = new HashSet<>();
        }
        return this.myFriends;
    }

    public void setMyFriends(Set<Person> friends) {
        this.myFriends = friends;
    }

    public Collection<Person> getFriendsTo() {
        if (this.friendsTo == null) {
            this.friendsTo = new HashSet<>();
        }
        return this.friendsTo;
    }

    public void setFriendsTo(Set<Person> friends) {
        this.friendsTo = friends;
    }

    public void addFriend(Person friend) {
        this.myFriends.add(friend);
        this.friendsTo.add(friend);
    }

    public String getUserRole() {
        if (this.userRole == null) {
            this.userRole = "";
        }
        return this.userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
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

    public void addMessage(Message message) {
        if (this.messages == null) {
            this.messages = new ArrayList<>();
        }
        this.messages.add(message);
    }

    public String getUnencryptedPassword() {
        if (this.unencryptedPassword == null) {
            this.unencryptedPassword = "";
        }
        return this.unencryptedPassword;
    }

    public void setUnencryptedPassword(String unencryptedPassword) {
        this.unencryptedPassword = unencryptedPassword;
    }
}
