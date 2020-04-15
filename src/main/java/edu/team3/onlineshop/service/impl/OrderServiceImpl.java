package edu.team3.onlineshop.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import edu.team3.onlineshop.dao.OrderRepository;
import edu.team3.onlineshop.domain.Order;
import edu.team3.onlineshop.service.OrderService;


@Service
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private OrderRepository orderRepository;

	@Override
	public Iterable<Order> getAll() {
		return this.orderRepository.findAll();
	}

	@Override
	public Optional<Order> get(Long id) {
		return this.orderRepository.findById(id);
	}

	@Override
	public Order save(Order order) {
		return this.orderRepository.save(order);
	}

	@Override
	public boolean delete(Long id) {
		Optional<Order> order = this.orderRepository.findById(id);
		
		if(!order.isPresent()) {
			System.err.println("The Order Object with ID : " + id + " does not exist in the database");
			return false;
		}
		else this.orderRepository.delete(order.get());
		return true;
		
	}

	@Override
	public Page<Order> findAllbyPageAndSize(int page, int size) {
		return this.orderRepository.findAll(this.getPageable(page, size));
	}

	@Override
	public Page<Order> findbyUser(String username, int page, int size) {
		return this.orderRepository.findAllByBuyer_Username(username , this.getPageable(page, size));
	}

	@Override
	public Optional<Order> getCurrentOrderOfUser(long userId , String username) {
		List<Order> orders = this.orderRepository.findAllByBuyer(userId , username);

		return orders.stream()
					.filter(order -> order.getPaid() == false)
					.sorted((o1, o2) -> o1.getId().compareTo(o2.getId()))
					.limit(1)
					.findFirst();
	}

	@Override
	public Optional<Order> getCurrentOrderOfUser(long userId) {
		List<Order> orders = this.orderRepository.findAllByBuyer(userId);

		return orders.stream()
					.filter(order -> order.getPaid() == false)
					.sorted((o1, o2) -> o1.getId().compareTo(o2.getId()))
					.limit(1)
					.findFirst();
	}

	@Override
	public Pageable getPageable(int page, int size){
		if(page < 0) page = 0;
		if(size <= 0) size = 10;
		Pageable pageable = PageRequest.of(page , size);
		return pageable;
	}
	
}
