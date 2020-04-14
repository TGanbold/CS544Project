package edu.team3.onlineshop.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import edu.team3.onlineshop.domain.OrderDetail;

public interface OrderDetailService {
	
	public Iterable<OrderDetail> getAll();
	public Page<OrderDetail> findAllbyPageAndSize(int page, int size);
	public Optional<OrderDetail> get(Long id);
	public OrderDetail save(OrderDetail orderDetail);
	public boolean delete(Long id);
	
}