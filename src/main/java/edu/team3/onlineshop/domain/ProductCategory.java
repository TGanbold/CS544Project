package edu.team3.onlineshop.domain;;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import edu.team3.onlineshop.View;

@Entity
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @JsonView(View.Summary.class)
    @NotBlank(message = "Category field required")
    @Column(nullable = false)
    private String category;

    //@JsonManagedReference
    @JsonIgnore
    @ManyToMany(mappedBy = "category")
    private Set<Product> products;

    @JsonView(View.Summary.class)
    private double taxInPercentage = 1.00;

    /**
     *
     */
    public ProductCategory() {
        super();
        // TODO Auto-generated constructor stub
    }





    /**
     * @param id
     */
    public ProductCategory(Long id) {
        super();
        this.id = id;
    }





    /**
     * @param category
     */
    public ProductCategory(@NotBlank(message = "Category field required") String category) {
        super();
        this.category = category;
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
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * @return the taxInPercentage
     */
    public double getTaxInPercentage() {
        return taxInPercentage;
    }

    /**
     * @param taxInPercentage the taxInPercentage to set
     */
    public void setTaxInPercentage(double taxInPercentage) {
        this.taxInPercentage = taxInPercentage;
    }

    /**
     * @return the products
     */
    public Set<Product> getProducts() {
        return products;
    }



    /**
     * @param products the products to set
     */
    public void setProducts(Set<Product> products) {
        this.products = products;
    }



    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((category == null) ? 0 : category.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        ProductCategory other = (ProductCategory) obj;
        if (category == null) {
            if (other.category != null)
                return false;
        } else if (!category.equals(other.category))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }




}