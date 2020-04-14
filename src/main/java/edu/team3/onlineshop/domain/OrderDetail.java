package edu.team3.onlineshop.domain;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Entity(name = "order_details")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String orderId;

    @ManyToOne(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    @NotNull
    private Product product;

    @NotNull
    @Column(nullable = false)
    @Min(0)
    private double discount;

    @NotNull
    @Column(nullable = false)
    @Min(1)
    private int quantity;

    @PastOrPresent
    @NotNull
    @Column(nullable = false)
    private LocalDate date;

    @NotNull @Min(0)
    @Column(nullable = false)
    private double price;

    @NotNull @Min(0)
    @Column(nullable = false)
    private double tax;

    public OrderDetail() {
    }

    public OrderDetail(long id, @NotNull String orderId, @NotNull Product product, @NotNull @Min(0) double discount,
			@NotNull @Min(1) int quantity, @PastOrPresent @NotNull LocalDate date, @NotNull @Min(0) double price,
			@NotNull @Min(0) double tax) {
		this.id = id;
		this.orderId = orderId;
		this.product = product;
		this.discount = discount;
		this.quantity = quantity;
		this.date = date;
		this.price = price;
		this.tax = tax;
	}

	public OrderDetail(String orderId, Product product, double discount, int quantity, LocalDate date) {
        this.orderId = orderId;
        this.product = product;
        this.quantity = quantity;
        this.date = date;
        this.discount = product.getDiscount() * quantity;
        this.price = product.getPrice() * quantity;
        this.tax = product.getCategory().getTaxInPercentage() * this.price;
    }
    
    public OrderDetail(String orderId, Product product, double discount, int quantity, LocalDate date, double price, double tax) {
        this.orderId = orderId;
        this.product = product;
        this.discount = discount;
        this.quantity = quantity;
        this.date = date;
        this.price = price;
        this.tax = tax;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

	public double getPrice() {
		return price;
	}

	public double getTax() {
		return tax;
	}

    public double computePrice() {
		double unitPrice = this.getProduct().getPrice();
		int quantity = this.getQuantity();
		return quantity * unitPrice;
	}

	public double computeTax() {
		double tax = this.getProduct().getCategory().getTaxInPercentage();
		return this.getPrice() * tax;
	}

	public double computeTotalPrice() {
		double price = this.getPrice();
		double discount = Double.valueOf(this.getDiscount());
		return price + this.getTax() - discount;
	}
    
}
