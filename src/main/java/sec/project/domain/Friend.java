package sec.project.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Friend extends AbstractPersistable<Long> {

    @NotBlank(message = "Give a username")
    private String username;

    @NotBlank(message = "Give a password")
    private String password;

    // Each Friend may be an admin in many Forums and each Forum may have many admins.
    @ManyToMany
    Collection<Forum> adminForums;

    // Each Friend may belong to many Forums and each Forum may have many Users.
    @ManyToMany
    Collection<Forum> memberForums;

    // Each Friend may belong to many FriendGroups, that is, s/he can be
    // a friend for many other Users.
    @ManyToMany(mappedBy = "friendsTo", fetch = FetchType.EAGER)
    private Collection<Friend> myFriends;

    @ManyToMany
    private Collection<Friend> friendsTo;

    public Friend() {
        this("", null);
    }

    public Friend(String username, String password) {
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

    public Collection<Friend> getMyFriends() {
        if (this.myFriends == null) {
            this.myFriends = new ArrayList<>();
        }
        return this.myFriends;
    }

    public void setMyFriends(List<Friend> friends) {
        this.myFriends = friends;
    }

    public Collection<Friend> getFriendsTo() {
        if (this.friendsTo == null) {
            this.friendsTo = new ArrayList<>();
        }
        return this.friendsTo;
    }

    public void setFriendsTo(List<Friend> friends) {
        this.friendsTo = friends;
    }
}
