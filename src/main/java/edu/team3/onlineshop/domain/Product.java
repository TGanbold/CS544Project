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
	@NotBlank(message = "Title field is required")
	private String title;

	@JsonView(View.Summary.class)
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="merchant_id")
	private Merchant merchant;

}
