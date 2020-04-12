package edu.team3.onlineshop.domain;

import com.fasterxml.jackson.annotation.JsonView;
import edu.team3.onlineshop.View;

import javax.persistence.*;
import java.util.List;

/**
 * @author team 3
 *
 */

@Entity(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonView(View.Summary.class)
    @Column(nullable = false, unique = true)
    private String type;

    @OneToMany(mappedBy = "role")
    private List<User> user;

    public Role() {
    }

    public Role(long id) {
        super();
        this.id = id;
    }



    public void setId(long id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public List<User> getUser() {
        return user;
    }
}
