package edu.team3.onlineshop.repository;

import edu.team3.onlineshop.domain.Merchant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author team 3
 *
 */

public interface MerchantRepository extends UserBaseRepository<Merchant> {

	Page<Merchant> findByApproved(boolean approved, Pageable pageable);
    
}
