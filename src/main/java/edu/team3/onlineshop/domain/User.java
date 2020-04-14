package edu.team3.onlineshop.domain;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;

import edu.team3.onlineshop.View;


/**
 * @author 
 *
 */

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
	// @NotNull(message = "Please provide at least one role.")
	private Role role;

	@OneToOne(cascade = CascadeType.ALL)
	private Address billingAddress;

	@OneToOne(cascade = CascadeType.ALL)
	private Address shippingAddress;

	public User() {

	}

	public User(Long id) {
		this.id = id;
	}

	/**
	 * @param firstName
	 * @param lastName
	 * @param password
	 * @param username
	 * @param role
	 * @param billingAddress
	 * @param shippingAddress
	 */
	public User(@NotEmpty(message = "Please provide a first name.") String firstName,
			@NotBlank @NotEmpty(message = "Please provide a last name.") String lastName,
			@NotBlank(message = "Please provide a password.") String password,
			@NotBlank(message = "Please provide an username.") @Email(message = "Please provide a valid email.") String username,
			@NotNull(message = "Please provide at least one role.") Role role, Address billingAddress,
			Address shippingAddress) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.username = username;
		this.role = role;
		this.billingAddress = billingAddress;
		this.shippingAddress = shippingAddress;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	/**
	 * @return the billingAddress
	 */
	public Address getBillingAddress() {
		return billingAddress;
	}

	/**
	 * @param billingAddress the billingAddress to set
	 */
	public void setBillingAddress(Address billingAddress) {
		this.billingAddress = billingAddress;
	}

	/**
	 * @return the shippingAddress
	 */
	public Address getShippingAddress() {
		return shippingAddress;
	}

	/**
	 * @param shippingAddress the shippingAddress to set
	 */
	public void setShippingAddress(Address shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id != other.id)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

}
