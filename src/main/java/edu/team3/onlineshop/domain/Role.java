package edu.team3.onlineshop.domain;

<<<<<<< HEAD
import javax.persistence.*;
=======
>>>>>>> 7847999b6c8148a519ca3217f436aa2be35e8856

import com.fasterxml.jackson.annotation.JsonView;

import edu.team3.onlineshop.View;
<<<<<<< HEAD

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
=======
import javax.persistence.*;
import java.util.List;
import java.util.Objects;

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
    
    public Role() {}
    
    
    

    /**
>>>>>>> 7847999b6c8148a519ca3217f436aa2be35e8856
	 * @param id
	 */
	public Role(long id) {
		super();
		this.id = id;
	}

<<<<<<< HEAD
=======



>>>>>>> 7847999b6c8148a519ca3217f436aa2be35e8856
	/**
	 * @param id
	 * @param type
	 */
	public Role(long id, String type) {
		super();
		this.id = id;
		this.type = type;
	}

<<<<<<< HEAD
=======

>>>>>>> 7847999b6c8148a519ca3217f436aa2be35e8856
	/**
	 * @param type
	 */
	public Role(String type) {
		super();
		this.type = type;
	}

<<<<<<< HEAD
	public String getType() {
		return type;
	}

=======



	public String getType() {
        return type;
    }
    
    

   
>>>>>>> 7847999b6c8148a519ca3217f436aa2be35e8856
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
<<<<<<< HEAD

=======
>>>>>>> 7847999b6c8148a519ca3217f436aa2be35e8856
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
<<<<<<< HEAD

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
=======
	private void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(type, role.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }
    public void removeuser(User user) {
        //this.user.remove(user);
    }

}

>>>>>>> 7847999b6c8148a519ca3217f436aa2be35e8856
