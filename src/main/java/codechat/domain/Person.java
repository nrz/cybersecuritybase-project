package codechat.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Person extends AbstractPersistable<Long> {

    @NotBlank(message = "Give a username")
    private String username;

    @NotBlank(message = "Give a password")
    private String password;

    // Each Person may be an admin in many Forums and each Forum may have many admins.
    @ManyToMany
    Collection<Forum> adminForums;

    // Each Person may belong to many Forums and each Forum may have many Persons.
    @ManyToMany
    Collection<Forum> memberForums;

    // Each Person may belong to many FriendGroups, that is, s/he can be
    // a friend for many other Persons.
    @ManyToMany(mappedBy = "friendsTo", fetch = FetchType.EAGER)
    private Collection<Person> myFriends;

    @ManyToMany
    private Collection<Person> friendsTo;

    public Person() {
        this("", null);
    }

    public Person(String username, String password) {
        this.username = username;
        this.password = password;
        this.adminForums = new ArrayList<>();
        this.memberForums = new ArrayList<>();
        this.myFriends = new ArrayList<>();
        this.friendsTo = new ArrayList<>();
    }

    public String getUsername() {
        if (this.username == null) {
            this.username = "";
        }
        return this.username;
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

    public Collection<Forum> getMemberForums() {
        if (this.memberForums == null) {
            this.memberForums = new ArrayList<>();
        }
        return this.memberForums;
    }

    public void setMemberForums(List<Forum> forums) {
        this.memberForums = forums;
    }

    public Collection<Person> getMyFriends() {
        if (this.myFriends == null) {
            this.myFriends = new ArrayList<>();
        }
        return this.myFriends;
    }

    public void setMyFriends(List<Person> friends) {
        this.myFriends = friends;
    }

    public Collection<Person> getFriendsTo() {
        if (this.friendsTo == null) {
            this.friendsTo = new ArrayList<>();
        }
        return this.friendsTo;
    }

    public void setFriendsTo(List<Person> friends) {
        this.friendsTo = friends;
    }
}