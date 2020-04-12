package edu.team3.onlineshop.domain;

import com.fasterxml.jackson.annotation.JsonView;
import edu.team3.onlineshop.View;

import javax.persistence.*;
import javax.validation.constraints.Email;
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

    @NotBlank(message = "Please provide a password.")
    private String password;

    private boolean enabled;

    @JsonView(View.Summary.class)
    @Column(unique = true)
    @NotBlank(message = "Please provide an username.")
    @Email(message = "Please provide a valid email.")
    private String username;

    @JsonView(View.Summary.class)
    @ManyToOne(cascade = CascadeType.MERGE)
    //@NotNull(message = "Please provide at least one role.")
    private Role role;

    @OneToOne(cascade = CascadeType.ALL)
    private Address billingAddress;

    @OneToOne(cascade = CascadeType.ALL)
    private Address shippingAddress;

    public User() {

    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getUsername() {
        return username;
    }

    public Role getRole() {
        return role;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }


}
