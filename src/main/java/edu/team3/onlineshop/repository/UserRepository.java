package edu.team3.onlineshop.repository;


import edu.team3.onlineshop.domain.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author team 3
 *
 */

@Transactional
public interface UserRepository extends UserBaseRepository<User> {
    Optional<User> findUserByUsername(String username);
    Optional<User> deleteUserByUsername(String username);
    Boolean existsUserById(Long id);
    Boolean existsUserByUsernameAndIdIsNot(String username, Long id);
    Iterable<User> findByRoleId(long role_id);
    User findByEmailIdIgnoreCase(String emailId);
    
}
