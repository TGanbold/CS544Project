package edu.team3.onlineshop.domain;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonView;

import edu.team3.onlineshop.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

	/**
	 * @param id
	 */
	public Role(long id) {
		super();
		this.id = id;
	}

	/**
	 * @param id
	 * @param type
	 */
	public Role(long id, String type) {
		super();
		this.id = id;
		this.type = type;
	}

	/**
	 * @param type
	 */
	public Role(String type) {
		super();
		this.type = type;
	}

	public String getType() {
		return type;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	private void setType(String type) {
		this.type = type;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Role role = (Role) o;
		return Objects.equals(type, role.type);
	}

	@Override
	public int hashCode() {
		return Objects.hash(type);
	}

	public void removeuser(User user) {
		
	}

}
