package edu.team3.onlineshop.dao;


import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.team3.onlineshop.domain.OrderDetail;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long>{

	public Optional<OrderDetail> findById(Long id);	
	public Page<OrderDetail> findAll(Pageable pageable);

}
