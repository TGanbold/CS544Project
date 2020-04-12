package edu.team3.onlineshop.service;


import edu.team3.onlineshop.domain.Merchant;
import edu.team3.onlineshop.domain.User;
import org.springframework.data.domain.Page;

import java.util.Optional;

/**
 * @author team 3
 *
 */
public interface UserService {
	public Optional<User> findUserByUsername(String username);
    public User saveUser(User account);
    public Iterable<User> listUsers();
    public Page<User> listBuyers(int page, int size);
    public Iterable<Merchant> listMerchants();
    public Page<Merchant> listMerchant(int page, int size);
    public void deleteUser(User user);
    public User updateUser(User user);
    public User setUserPassword(String username, String newPassword);
	public User findUserById(Long userId);
	public Optional<Merchant> findMerchantById(Long userId);
	
	public Page<Merchant> getListByApproveStatus(int status, int page, int size);
}
