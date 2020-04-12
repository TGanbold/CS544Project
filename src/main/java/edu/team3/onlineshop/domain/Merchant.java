package edu.team3.onlineshop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import edu.team3.onlineshop.View;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author team 3
 *
 */
@Entity(name = "Merchants")
public class Merchant extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = -905427482016727473L;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Address officeAddress;
	
	@JsonView(View.Summary.class)
	@NotBlank(message = "Business Name field is required")
	@Column( nullable = false)
	private String bizName;
	
	@NotBlank(message = "Office Phone 1 field is required")
	@Column(name="office_phone_1", nullable = false)
	private String officePhoneNumber1;
	
	@Column(name="office_phone_2", nullable = true)
	private String officePhoneNumber2;
	
	@Column(name="identity_proof_img_url", nullable = true)
	private String identityProof;
	
	@JsonView(View.Summary.class)
	@Column(name="can_sell")
	private boolean approved = false;
	
	@JsonIgnore
	@OneToMany(mappedBy = "merchant" ,cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Product> products;
	
	
	/**
	 * 
	 */
	public Merchant() {
		super();
	}
	
	
	
	
	/**
	 * 
	 */
	public Merchant(long userId) {
		super();
	}




	/**
	 * @param firstName
	 * @param lastName
	 * @param password
	 * @param username
	 * @param role
	 * @param billingAddress
	 * @param shippingAddress
	 * @param officeAddress
	 * @param officePhoneNumber1
	 * @param officePhoneNumber2
	 * @param identityProof
	 * @param approved
	 * @param products
	 */
	public Merchant(@NotEmpty(message = "Please provide a first name.") String firstName,
                    @NotBlank @NotEmpty(message = "Please provide a last name.") String lastName,
                    @NotBlank(message = "Please provide a password.") String password,
                    @NotBlank(message = "Please provide an username.") @Email(message = "Please provide a valid email.") String username,
                    @NotNull(message = "Please provide at least one role.") Role role, Address billingAddress,
                    Address shippingAddress, Address officeAddress,
                    @NotBlank(message = "This field is required") String officePhoneNumber1, String officePhoneNumber2,
                    String identityProof, boolean approved, List<Product> products) {
		super(firstName, lastName, password, username, role, billingAddress, shippingAddress);
		this.officeAddress = officeAddress;
		this.officePhoneNumber1 = officePhoneNumber1;
		this.officePhoneNumber2 = officePhoneNumber2;
		this.identityProof = identityProof;
		this.approved = approved;
		this.products = products;
	}


	/**
	 * @return the officeAddress
	 */
	public Address getOfficeAddress() {
		return officeAddress;
	}
	/**
	 * @param officeAddress the officeAddress to set
	 */
	public void setOfficeAddress(Address officeAddress) {
		this.officeAddress = officeAddress;
	}
	/**
	 * @return the officePhoneNumber1
	 */
	public String getOfficePhoneNumber1() {
		return officePhoneNumber1;
	}
	/**
	 * @param officePhoneNumber1 the officePhoneNumber1 to set
	 */
	public void setOfficePhoneNumber1(String officePhoneNumber1) {
		this.officePhoneNumber1 = officePhoneNumber1;
	}
	/**
	 * @return the officePhoneNumber2
	 */
	public String getOfficePhoneNumber2() {
		return officePhoneNumber2;
	}
	/**
	 * @param officePhoneNumber2 the officePhoneNumber2 to set
	 */
	public void setOfficePhoneNumber2(String officePhoneNumber2) {
		this.officePhoneNumber2 = officePhoneNumber2;
	}
	/**
	 * @return the identityProof
	 */
	public String getIdentityProof() {
		return identityProof;
	}
	/**
	 * @param identityProof the identityProof to set
	 */
	public void setIdentityProof(String identityProof) {
		this.identityProof = identityProof;
	}
	/**
	 * @return the approved
	 */
	public boolean isApproved() {
		return approved;
	}
	/**
	 * @param approved the approved to set
	 */
	public void setApproved(boolean approved) {
		this.approved = approved;
	} 
	
	
}
