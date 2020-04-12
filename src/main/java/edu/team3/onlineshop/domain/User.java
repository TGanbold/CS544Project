package edu.team3.onlineshop.domain;

import com.fasterxml.jackson.annotation.JsonView;
import edu.team3.onlineshop.View;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Entity(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -1797511201371820987L;

    @JsonView(View.Summary.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", updatable = false, nullable = false)
    private long id;

    @NotEmpty(message = "Please provide a first name.")
    private String firstName;

    @NotBlank
    @NotEmpty(message = "Please provide a last name.")
    private String lastName;

}
