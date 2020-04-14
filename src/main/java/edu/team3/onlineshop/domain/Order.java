package edu.team3.onlineshop.domain;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(nullable = false)
    private String orderNumber;

    @NotNull
    @OneToOne
    private User buyer;

    @NotNull
    @Column(nullable = false)
    private LocalDate date;

    @NotNull
    @Column(nullable = false)
    private boolean paid;

    @NotNull
    @Column(nullable = false)
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails = new ArrayList<>();

    @NotNull  
    @Min(value = 0,message = "{minNumber}")
    @Column(nullable = false)
    private double discount;

    @NotNull 
    @Min(value = 0,message = "{minNumber}")
    @Column(nullable = false)
    private double price;

    @NotNull   
    @Min(value = 0,message = "{minNumber}")
    @Column(nullable = false)
    private double tax;

    public Order() {
    }
    

    public Order(Long id, @NotEmpty String orderNumber, @NotNull User buyer,
			@NotNull LocalDate date, @NotNull boolean paid, @NotNull List<OrderDetail> orderDetails,
			@NotNull @Min(0) double discount, @NotNull @Min(0) double price, @NotNull @Min(0) double tax) {
		this.id = id;
		this.orderNumber = orderNumber;
		this.buyer = buyer;
		this.date = date;
		this.paid = paid;
		this.orderDetails = orderDetails;
		this.discount = discount;
		this.price = price;
		this.tax = tax;
	}


	public Order(String orderID, User buyer, LocalDate date, Boolean paid, double discount, double price, double tax, List<OrderDetail> orderDetails ) {
        this.orderNumber = orderID;
        this.buyer = buyer;
        this.date = date;
        this.paid = paid;
        this.discount = discount;
        this.price = price;
        this.tax = tax;
        this.orderDetails = orderDetails;
    }

    public Order(String orderID, User buyer, LocalDate date, Boolean paid, double discount, double price, double tax) {
    	this.orderNumber = orderID;
        this.buyer = buyer;
        this.date = date;
        this.paid = paid;
        this.discount = discount;
        this.price = price;
        this.tax = tax;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderID() {
        return orderNumber;
    }

    public void setOrderID(String orderID) {
        this.orderNumber = orderID;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }
    
    public void addOrderDetail(OrderDetail orderDetail) {
    	this.orderDetails.add(orderDetail);
    }

	public double getPrice() {
		return price;
	}

	public double getTax() {
		return tax;
	}
    
	public double computeTax() {
		this.tax = this.getOrderDetails().stream()
				.map(OrderDetail::computeTax)
				.collect(Collectors.summingDouble(Double::doubleValue)); 
		return this.tax; 
	}

	public double computePrice() {
		this.price = this.orderDetails.stream()
			.map(OrderDetail::computePrice)
			.collect(Collectors.summingDouble(Double::doubleValue));
		return this.price;
	}
	
	public Double computeTotalPrice() {
		return this.orderDetails.stream()
			.map(OrderDetail::computeTotalPrice)
			.collect(Collectors.summingDouble(Double::doubleValue));
	}
	
	public double computeTotalDiscount() {
		this.discount = this.orderDetails.stream()
			.map(OrderDetail::getDiscount)
			.collect(Collectors.summingDouble(Double::doubleValue));
		return this.discount;
	}
}
