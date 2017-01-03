package sec.project.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class User extends AbstractPersistable<Long> {

    @NotBlank(message = "Given an username")
    private String username;

    @NotBlank(message = "Give a password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    List<Forum> forums;

    public User() {
        this.username = "";
        this.forums = new ArrayList<>();
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.forums = new ArrayList<>();
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

    public List<Forum> getForums() {
        if (this.forums == null) {
            this.forums = new ArrayList<>();
        }
        return forums;
    }

    public void setForums(List<Forum> forums) {
        this.forums = forums;
    }
}
