package edu.team3.onlineshop.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonView;
import edu.team3.onlineshop.View;


@Entity(name = "product_image")
public class ProductImage {

    @JsonView(View.Summary.class)
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonView(View.Summary.class)
    @NotBlank(message = "File Image Required")
    private String imageUrl;

    public ProductImage() {

    }

    /**
     * @param imageUrl
     */
    public ProductImage(@NotBlank(message = "File Image Required") String imageUrl) {
        super();
        this.imageUrl = imageUrl;
    }

    /**
     * @param id
     */
    public ProductImage(Long id) {
        super();
        this.id = id;
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
     * @return the imageUrl
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * @param imageUrl the imageUrl to set
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return String.format("ProductImage [id=%s, imageUrl=%s]", id, imageUrl);
    }


}
