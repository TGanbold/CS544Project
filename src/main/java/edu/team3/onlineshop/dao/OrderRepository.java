package edu.team3.onlineshop.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.team3.onlineshop.domain.Order;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{
	
	public Optional<Order> findById(Long id);
	
	public Page<Order> findAll(Pageable pageable);

	public Page<Order> findAllByBuyer_Username(String username, Pageable pageable);

	@Query("select o from orders o inner join o.buyer b where b.id = :id and b.username = :username")
	public List<Order> findAllByBuyer(@Param(value = "id") long userId , @Param(value = "username") String username);

	@Query("select o from orders o inner join o.buyer b where b.id = :id")
	public List<Order> findAllByBuyer(@Param(value = "id") long userId );
	
}
