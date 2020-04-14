package edu.team3.onlineshop.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import edu.team3.onlineshop.dao.OrderDetailRepository;
import edu.team3.onlineshop.domain.OrderDetail;
import edu.team3.onlineshop.service.OrderDetailService;


@Service
public class OrderDetailServiceImpl implements OrderDetailService{
	
	@Autowired
	private OrderDetailRepository orderDetailRepository;

	@Override
	public Iterable<OrderDetail> getAll() {
		return this.orderDetailRepository.findAll();
	}

	@Override
	public Optional<OrderDetail> get(Long id) {
		return this.orderDetailRepository.findById(id);
	}

	@Override
	public OrderDetail save(OrderDetail orderDetail) {
		return this.orderDetailRepository.save(orderDetail);
	}

	@Override
	public boolean delete(Long id) {
		Optional<OrderDetail> orderDetail = this.orderDetailRepository.findById(id);
		if(!orderDetail.isPresent()) {
			System.err.println("The Order Detail Object with ID : " + id + " does not exist in the database");
			return false; 
		}
		else this.orderDetailRepository.delete(orderDetail.get());
		return true;
	}

	@Override
	public Page<OrderDetail> findAllbyPageAndSize(int page, int size) {
		if(page < 0) page = 0;
		if(size <= 0) size = 10;
		Pageable pageable = PageRequest.of(page , size);
		
		return orderDetailRepository.findAll(pageable);
	}
}