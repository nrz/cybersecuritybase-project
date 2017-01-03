package sec.project.domain;

import javax.persistence.Entity;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Forum extends AbstractPersistable<Long> {

    private String name;

    public Forum() {
        this.name = "New forum";
    }

    public Forum(String name) {
        this.name = name;
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
}
