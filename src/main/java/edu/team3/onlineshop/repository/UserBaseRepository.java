package edu.team3.onlineshop.repository;


import edu.team3.onlineshop.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * @author team 3
 *
 */

@Repository
public interface UserBaseRepository<T extends User> extends JpaRepository<T, Long> {

}
