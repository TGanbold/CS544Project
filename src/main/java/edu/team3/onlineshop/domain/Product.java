package edu.team3.onlineshop.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import edu.team3.onlineshop.View;
import org.springframework.stereotype.Indexed;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;

import cs425.team4.eshopper.View;

@Entity(name = "products")
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"merchant_id","title"})})
public class Product {
	@JsonView(View.Summary.class)
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonView(View.Summary.class)
	@NotBlank(message = "Product Number field is required")
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
	@NotNull(message = "Discount field is required")
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

	//@JsonBackReference
	@ManyToOne(/*cascade = CascadeType.ALL*/)
//	@JoinTable(
//			name = "products_categories",
//			joinColumns = @JoinColumn(name ="product_id"),
//			inverseJoinColumns = @JoinColumn(name ="category_id")
//	)


	@NotNull(message = "Category field is required")
	private ProductCategory category;

	public Product(Long id, @NotBlank(message = "Title field is required") String title,
				   @NotBlank(message = "Summary field is required") String summary,
				   @NotBlank(message = "Description field is required") String description,
				   @NotNull(message = "Discount field is required") double discount,
				   @NotNull(message = "Price field is required") double price,
				   @NotNull(message = "Quantity Available field is required") long qtyAvail, boolean isAvailable,
				   Merchant merchant, List<ProductImage> images,
				   @NotNull(message = "Category field is required") ProductCategory category) {
		this.id = id;
		this.title = title;
		this.summary = summary;
		this.description = description;
		this.discount = discount;
		this.price = price;
		this.qtyAvail = qtyAvail;
		this.isAvailable = isAvailable;
		this.merchant = merchant;
		this.images = images;
		this.category = category;
	}

	public Product(@NotBlank(message = "Title field is required") String title,
				   @NotBlank(message = "Summary field is required") String summary,
				   @NotBlank(message = "Description field is required") String description,
				   @NotNull(message = "Discount field is required") double discount,
				   @NotNull(message = "Price field is required") double price,
				   @NotNull(message = "Quantity Available field is required") long qtyAvail, boolean isAvailable,
				   Merchant merchant, List<ProductImage> images,
				   @NotNull(message = "Category field is required") ProductCategory category) {
		this.title = title;
		this.summary = summary;
		this.description = description;
		this.discount = discount;
		this.price = price;
		this.qtyAvail = qtyAvail;
		this.isAvailable = isAvailable;
		this.merchant = merchant;
		this.images = images;
		this.category = category;
	}

	/**
	 *
	 */
	public Product() {
		super();
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}



	/**
	 * @return the productNumber
	 */
	public String getProductNumber() {
		return productNumber;
	}

	/**
	 * @param productNumber the productNumber to set
	 */
	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * @param summary the summary to set
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the category
	 */
	public ProductCategory getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(ProductCategory category) {
		this.category = category;
	}

	/**
	 * @return the discount
	 */
	public double getDiscount() {
		return discount;
	}

	/**
	 * @param discount the discount to set
	 */
	public void setDiscount(double discount) {
		this.discount = discount;
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @return the qtyAvail
	 */
	public long getQtyAvail() {
		return qtyAvail;
	}

	/**
	 * @param qtyAvail the qtyAvail to set
	 */
	public void setQtyAvail(long qtyAvail) {
		this.qtyAvail = qtyAvail;
	}

	/**
	 * @return the merchant
	 */
	public Merchant getMerchant() {
		return merchant;
	}

	/**
	 * @param merchant the merchant to set
	 */
	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}


	/**
	 * @return the images
	 */
	public List<ProductImage> getImages() {
		return images;
	}

	/**
	 * @param images the images to set
	 */
	public void setImages(List<ProductImage> images) {
		this.images = images;
	}

	/**
	 * @param images the images to set
	 */
	public void setImage(ProductImage image) {
		this.images.add(image);
	}

	/**
	 * @return the isAvailable
	 */
	public boolean isAvailable() {
		return isAvailable;
	}

	/**
	 * @param isAvailable the isAvailable to set
	 */
	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}




	@Override
	public String toString() {
		return String.format(

				"Product [id=%s, title=%s, summary=%s, description=%s, discount=%s, price=%s, qtyAvail=%s, isAvailable=%s, merchant=%s, images=%s, category=%s]",
				id, title, summary, description, discount, price, qtyAvail, isAvailable, merchant, images, category);

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((merchant == null) ? 0 : merchant.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (merchant == null) {
			if (other.merchant != null)
				return false;
		} else if (!merchant.equals(other.merchant))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}









}