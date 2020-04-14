package edu.team3.onlineshop.repository;

import edu.team3.onlineshop.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
	Optional<Product> findById(long id);
	Page<Product> findByTitleContaining(String title, Pageable pageable);
	
	@Query("select p from products p where p.category.category = ?1")
    Page<Product> findByCategory(String category, Pageable pageable);
	
	Iterable<Product> findByMerchantId(long merchantId);
	Iterable<Product> findByCategoryId(long categoryId);
}
