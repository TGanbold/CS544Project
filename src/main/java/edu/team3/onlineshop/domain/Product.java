package edu.team3.onlineshop.domain;

import com.fasterxml.jackson.annotation.JsonView;
import edu.team3.onlineshop.View;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author team 3
 *
 */

@Entity(name = "products")
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"merchant_id","title"})})
public class Product {
	@JsonView(View.Summary.class)
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonView(View.Summary.class)
	@NotBlank(message = "Product Number field is  required")
	private String productNumber;

	@JsonView(View.Summary.class)
	@NotBlank(message = "Title field is required")
	private String title;

	@JsonView(View.Summary.class)
	@NotBlank(message = "Summary field is required")
	private String summary;

	@JsonView(View.Summary.class)
	@NotBlank(message = "Description field is required")
	private String description;

	@JsonView(View.Summary.class)
	@NotBlank(message = "Discount field is required")
	private double discount;

	@JsonView(View.Summary.class)
	@NotNull(message = "Price field is required")
	private double price;

	@JsonView(View.Summary.class)
	@NotNull(message = "Quantity Available field is required")
	private long qtyAvail;

	@JsonView(View.Summary.class)
	private boolean isAvailable = true;

	@JsonView(View.Summary.class)
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="merchant_id")
	private Merchant merchant;

	@JsonView(View.Summary.class)
	@ManyToMany(cascade = CascadeType.ALL)
	private List<ProductImage> images = new ArrayList<ProductImage>();

}
