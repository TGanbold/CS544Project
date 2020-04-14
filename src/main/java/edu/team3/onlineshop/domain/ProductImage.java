package edu.team3.onlineshop.domain;

import com.fasterxml.jackson.annotation.JsonView;
import edu.team3.onlineshop.View;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

public class ProductImage {

    @JsonView(View.Summary.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonView(View.Summary.class)
    @NotBlank(message = "File Image Required")
    private String imageUrl;

    public ProductImage() {

    }

    public ProductImage(@NotBlank(message = "File Image Required") String imageUrl) {
        super();
        this.imageUrl = imageUrl;
    }

    public ProductImage(Long id) {
        super();
        this.id = id;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getImageUrl() { return imageUrl; }

    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    @Override
    public String toString() { return  String.format("ProductImage [id=%s, imageUrl=%s]", id, imageUrl); }

}
