package edu.team3.onlineshop.service.impl;

import edu.team3.onlineshop.domain.Merchant;
import edu.team3.onlineshop.domain.User;
import edu.team3.onlineshop.repository.MerchantRepository;
import edu.team3.onlineshop.repository.UserRepository;
import edu.team3.onlineshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author team 3
 *
 */

@Service
public class UserServiceImpl implements UserService{

	private static final long serialVersionUID = -4677657640652141814L;
	private String username;
	private String password;
	private User user;
	private boolean isActive;
	private List<GrantedAuthority> authorities;

	public UserServiceImpl(User user) {
		this.user = user;
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.isActive = user.isIsEnabled();
		this.authorities = new ArrayList<>();
		if(user.getRole() != null) {
			this.authorities.add(new SimpleGrantedAuthority(user.getRole().getType()));
		}

	}

	private UserRepository userRepository;
	private MerchantRepository merchantRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, MerchantRepository merchantRepository) {
		this.userRepository = userRepository;
		this.merchantRepository = merchantRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<User> findUserByUsername(String username) {
		return userRepository.findUserByUsername(username);
	}

	@Transactional(readOnly = true)
	public void validateUniqueUserFields(@Valid User account) {
		if (userRepository.existsUserByUsernameAndIdIsNot(account.getUsername(), account.getId())) {
			throw new ValidationException("Username " + account.getUsername() + " is already taken.");
		}
		
	}

	@Transactional
	@Override
	public User saveUser(@Valid User account) {
		validateUniqueUserFields(account);
		return (User) userRepository.save(account);
	}

	@Transactional(readOnly = true)
	public Iterable<User> listUsers() {
		return userRepository.findAll();
	}

	@Transactional
	public void deleteUser(User user) {
		userRepository.delete(user);
	}

	@Transactional
	public User updateUser(@Valid User newUser) {
		validateUniqueUserFields(newUser);
		return (User) userRepository.save(newUser);
	}

	@Transactional
	public User setUserPassword(String username, String newPassword) {
		return userRepository.findUserByUsername(username).map((user) -> {
			user.setPassword(newPassword);
			return userRepository.save(user);
		}).orElseThrow(() -> new UsernameNotFoundException("Invalid username."));
	}

	@Transactional(readOnly = true)
	@Override
	public User findUserById(Long userId) {
		return (User) userRepository.findById(userId).get();
	}

	@Override
	public Iterable<Merchant> listMerchants() {
		return merchantRepository.findAll();
	}

	@Override
	public Optional<Merchant> findMerchantById(Long userId) {
		return merchantRepository.findById(userId);
	}

	@Override

	public Page<Merchant> getListByApproveStatus(int status, int page, int size) {
		if (page < 0) page = 0;
		if (size <= 0) size = 10;
		Pageable pageable = PageRequest.of(page, size);
		boolean stat = status == 1 ? true : false;
		return merchantRepository.findByApproved(stat, pageable);
	}

	public Page<User> listBuyers(int page, int size) {
		if(page < 0) page = 0;
		if(size <= 0) size = 10;
		Pageable pageable = PageRequest.of(page , size, Sort.by("firstName"));
		return userRepository.findAll(pageable);
	}

	@Override
	public Page<Merchant> listMerchant(int page, int size) {
		if(page < 0) page = 0;
		if(size <= 0) size = 10;
		Pageable pageable = PageRequest.of(page , size, Sort.by("firstName"));
		return merchantRepository.findAll(pageable);
	}

}
