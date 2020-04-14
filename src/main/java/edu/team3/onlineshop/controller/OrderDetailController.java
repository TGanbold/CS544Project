package edu.team3.onlineshop.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.team3.onlineshop.domain.OrderDetail;
import edu.team3.onlineshop.service.OrderDetailService;


@RestController
@RequestMapping(value = "/api/v1/order-details")
public class OrderDetailController {

	@Autowired
	private OrderDetailService orderDetailService;

	
	@Secured({"ROLE_BUYER" , "ROLE_ADMIN"})
	@GetMapping(value = {"/" , "/list"})
	public Page<OrderDetail> getAllOrderDetailsByPageAndSize(
			@RequestParam(name = "page" , defaultValue = "0") int page, 
			@RequestParam(name = "size" , defaultValue = "10") int size){
		return this.orderDetailService.findAllbyPageAndSize(page, size);
	}
	

	public Iterable<OrderDetail> getAll(){
		return this.orderDetailService.getAll();
	}
	
	@Secured({"ROLE_BUYER" , "ROLE_ADMIN"})
	@GetMapping(value = "{/{id}")
	public OrderDetail getOrderDetailsById(@PathVariable(name = "id") long id) {
		if(id < 0) return null;
		
		Optional<OrderDetail> orderDetail = this.orderDetailService.get(id);
		if(orderDetail.isPresent()) return orderDetail.get();
		else return null;
	}
	
	
	@Secured({"ROLE_ADMIN", "ROLE_BUYER"})
	@DeleteMapping(value = "/{id}")
	public boolean deleteOrderDetail(@RequestParam(name = "id") long id) {
		if(id < 0) return false;
		
		return this.orderDetailService.delete(id);
	}
	
	@Secured({"ROLE_ADMIN"})
	@PostMapping("/")
	public OrderDetail save(@Valid @RequestBody OrderDetail order) {
		return this.orderDetailService.save(order);
	}
	
}
