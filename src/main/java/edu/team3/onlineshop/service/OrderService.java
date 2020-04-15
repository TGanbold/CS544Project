package edu.team3.onlineshop.service;

import java.util.Optional;

import org.springframework.data.domain.Page;


import org.springframework.data.domain.Pageable;

import edu.team3.onlineshop.domain.Order;

public interface OrderService {
	
	public Iterable<Order> getAll();
	public Page<Order> findAllbyPageAndSize(int page, int size);
	
	public Page<Order> findbyUser(String username, int page, int size);
	
	public Optional<Order> get(Long id);
	public Order save(Order order);
	public boolean delete(Long id);
	
	public Optional<Order> getCurrentOrderOfUser(long userId , String username);
	public Optional<Order> getCurrentOrderOfUser(long userId);

	Pageable getPageable(int page, int size);
}
