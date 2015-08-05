package net.edgefox.spring.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by ilyutov on 05.08.15.
 */
@Entity
public class Event {
    @Id
    @GeneratedValue
    private long id;
    @NotEmpty(message = "Event title should not be empty")
    private String title;
    @ManyToMany(cascade = CascadeType.ALL)
    private Collection<User> users;

    public Event() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @JsonIgnore
    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }
}
