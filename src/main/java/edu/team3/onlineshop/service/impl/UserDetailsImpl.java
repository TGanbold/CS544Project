package edu.team3.onlineshop.service.impl;

import edu.team3.onlineshop.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author team 3
 *
 */

public class UserDetailsImpl implements UserDetails {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4677657640652141814L;
	private String username;
    private String password;
    private User user;
    private boolean isActive;
    private List<GrantedAuthority> authorities;

    public UserDetailsImpl(User user) {
    	this.user = user;
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.isActive = user.isIsEnabled();
        this.authorities = new ArrayList<>();
        if(user.getRole() != null) {
        	this.authorities.add(new SimpleGrantedAuthority(user.getRole().getType()));
        }
        
    }

    public UserDetailsImpl() {}
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.isActive;
    }

	public User getUser() {
		return user;
	}


    
    
}
