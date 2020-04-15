package edu.team3.onlineshop.controller;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.team3.onlineshop.domain.Order;
import edu.team3.onlineshop.domain.OrderDetail;
import edu.team3.onlineshop.domain.Product;
import edu.team3.onlineshop.domain.User;
import edu.team3.onlineshop.service.OrderService;
import edu.team3.onlineshop.service.ProductService;
import edu.team3.onlineshop.service.UserService;
import edu.team3.onlineshop.utils.SequenceGenerator;

@RestController
@CrossOrigin(allowedHeaders = "*")
@RequestMapping(value = "/api/v1/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProductService productService;
	
	@Secured({"ROLE_BUYER" , "ROLE_ADMIN"})
	@GetMapping(value = {"/" , "/list"})
	public Page<Order> getAllOrderByPageAndSize(
			@RequestParam(name = "page" , defaultValue = "0") int page,
			@RequestParam(name = "size" , defaultValue = "10") int size){

		String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
		System.out.println(currentUsername);
		
		Collection<? extends GrantedAuthority> roles = SecurityContextHolder
				.getContext().getAuthentication().getAuthorities();
		if(roles.contains("ROLE_ADMIN")) 
			return this.orderService.findAllbyPageAndSize(page, size);
		
		return this.orderService.findbyUser(currentUsername, page, size);
	}
	
	public Iterable<Order> getAllOrder(){
		return this.orderService.getAll();
	}
	
	@Secured({"ROLE_BUYER" , "ROLE_ADMIN"})
	@GetMapping(value = "/{id}")
	public ResponseEntity<Order> getOrderById(@PathVariable(name = "id") long id) {
		if(id < 0) return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		
		String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
		Collection<? extends GrantedAuthority> roles = SecurityContextHolder
				.getContext().getAuthentication().getAuthorities();
		
		Optional<Order> order = this.orderService.get(id);
		if(order.isPresent() 
				&& (order.get().getBuyer().getUsername().equals(currentUsername) 
				|| roles.contains("ROLE_ADMIN"))) 
			return new ResponseEntity<>(order.get(), HttpStatus.OK);
		else return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_BUYER"})
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Boolean> deleteOrder(@RequestParam(name = "id") long id) {
		if(id < 0) return new ResponseEntity<>(false, HttpStatus.EXPECTATION_FAILED);
		
		String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
		Collection<? extends GrantedAuthority> roles = SecurityContextHolder
				.getContext().getAuthentication().getAuthorities();
		
		Optional<Order> orderOpt = this.orderService.get(id);
		if(!orderOpt.isPresent()) {
			MultiValueMap<String , String> headers = new LinkedMultiValueMap<>();
			headers.add("error", "Order not found");
			return new ResponseEntity<>(false, headers, HttpStatus.EXPECTATION_FAILED);
		}
		
		Order order = orderOpt.get();
		if(order.getBuyer().getUsername().equals(currentUsername) || roles.contains("ROLE_ADMIN"))
			return new ResponseEntity<>(this.orderService.delete(id), HttpStatus.OK);
		
		return new ResponseEntity<>(false, HttpStatus.EXPECTATION_FAILED);
	}
	
	@Secured({"ROLE_ADMIN"})
	@PostMapping("/")
	public Order save(@Valid @RequestBody Order order) {
		return this.orderService.save(order);
	}
	
	@Secured({"ROLE_BUYER"})
	@PostMapping(value = "/add-cart", consumes = "application/json")
	public ResponseEntity<Order> createNewOrder(@RequestBody HashMap<String, String> data) {
		
		long productId = Long.parseLong(data.get("productId"));
		long userId = Long.parseLong(data.get("userId"));
		int quantity = Integer.parseInt(data.get("quantity"));
		String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
		
		if(productId < 0 || quantity < 0 || userId < 0) 
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
	
		User user = this.userService.findUserById(userId);
		if(user == null || !user.getUsername().equals(currentUsername)) {
			MultiValueMap<String , String> headers = new LinkedMultiValueMap<>();
			headers.add("error", "Invalid User");
			return new ResponseEntity<>(headers, HttpStatus.EXPECTATION_FAILED);
		}
		
		Optional<Product> product = this.productService.get(productId);
		if(!product.isPresent()) {
			MultiValueMap<String , String> headers = new LinkedMultiValueMap<>();
			headers.add("error", "Product Not Found");
			return new ResponseEntity<>(headers, HttpStatus.EXPECTATION_FAILED);
		}
		
		if(product.get().getMerchant().equals(user)) {
			MultiValueMap<String , String> headers = new LinkedMultiValueMap<>();
			headers.add("error", "This user cannot buy his own product");
			return new ResponseEntity<>(headers, HttpStatus.EXPECTATION_FAILED);
		}
		
		Order order;
		LocalDate date = LocalDate.now();
		Optional<Order> orderOpt = this.orderService.getCurrentOrderOfUser(userId, user.getUsername());
		if (orderOpt.isPresent() && orderOpt.get().getBuyer().equals(user)) {
			order = orderOpt.get();
			
		}else {
			System.out.println("---- Creating new Order ----");
			order = new Order();
			order.setOrderID(SequenceGenerator.getInstance().getNext());
			order.setBuyer(user);
			order.setDate(date);
		}
		
		OrderDetail orderDetail = new OrderDetail(order.getOrderID(), product.get(), product.get().getDiscount(), quantity, date);
		order.addOrderDetail(orderDetail);
		order = orderService.save(order);
		
		return new ResponseEntity<Order>(order, HttpStatus.CREATED);
	}
	
	@Secured({"ROLE_BUYER"})
	@PutMapping(value = "/checkout")
	public ResponseEntity<Order> checkout(@RequestBody HashMap<String, String> payload) {
		
		String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
		System.out.println(currentUsername);
		
		long orderId = Long.parseLong(payload.get("orderId"));
		if(orderId < 0) 
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		
		Optional<Order> orderOpt = this.orderService.get(orderId);
		if(!orderOpt.isPresent()) {
			MultiValueMap<String , String> headers = new LinkedMultiValueMap<>();
			headers.add("error", "Order Not Found");
			return new ResponseEntity<>(headers, HttpStatus.EXPECTATION_FAILED);
		}
		
		if(!orderOpt.get().getBuyer().getUsername().equals(currentUsername)) {
			MultiValueMap<String , String> headers = new LinkedMultiValueMap<>();
			headers.add("error", "Invalid User");
			return new ResponseEntity<>(headers, HttpStatus.EXPECTATION_FAILED);
		}
		
		Order order = orderOpt.get();
		order.computePrice();
		order.computeTax();
		order.computeTotalDiscount();
		order.setPaid(true);
		order = this.orderService.save(order);
		
		return new ResponseEntity<>(order, HttpStatus.OK);
	}
	
	@Secured({"ROLE_BUYER" , "ROLE_ADMIN"})
	@GetMapping(value = {"/test"})
	public Collection<? extends GrantedAuthority> test(){

		Collection<? extends GrantedAuthority> roles = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		for(GrantedAuthority x : roles) {
			System.out.println(x.getAuthority());
		}
		return roles;
	}
	
	
}
