/**
 * 
 */
package edu.team3.onlineshop.controller;

import com.fasterxml.jackson.annotation.JsonView;

import edu.team3.onlineshop.View;
import edu.team3.onlineshop.domain.Merchant;
import edu.team3.onlineshop.domain.Product;
import edu.team3.onlineshop.domain.ProductImage;
import edu.team3.onlineshop.domain.User;
import edu.team3.onlineshop.exceptions.ItemNotFoundException;
import edu.team3.onlineshop.service.FileStorageService;
import edu.team3.onlineshop.service.ProductService;
import edu.team3.onlineshop.service.RabbitMQSender;
import edu.team3.onlineshop.service.UserService;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Optional;

import javax.validation.Valid;


/**
 * @author team 3
 *
 */
@RestController
@CrossOrigin(allowedHeaders = "*")
@RequestMapping("/api/v1/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FileStorageService fileStorageService;
	
	@Autowired
	RabbitTemplate rabbitTemplate;

	@Autowired
	RabbitMQSender rabbitMQSender;

	private AmqpTemplate amqpTemplate;
	
	
//	/**
//	 * @param fileStorageService
//	 */
//	public ProductController(FileStorageService fileStorageService) {
//		super();
//		this.fileStorageService = fileStorageService;
//	}

	@JsonView(View.Summary.class)
	@Secured({"IS_AUTHENTICATED_ANONYMOUSLY"})
	@GetMapping(value = { "/list/page/"})
	public Page<Product> fetchProduct(
			@RequestParam(name = "page" , defaultValue = "0") int page,
			@RequestParam(name = "size" , defaultValue = "10") int size){
		return productService.findAllbyPageAndSize(page, size); 
	}
	
	@JsonView(View.Summary.class)
	@Secured(value = {"ROLE_MERCHANT", "ROLE_ADMIN"})
	@GetMapping(value = { "/list/{merchantAccount}"})
	public Iterable<Product> fetchAllProductByMerchant(@PathVariable("merchantAccount") String merchantAccount) throws Exception{
		User m = userService.findUserByUsername(merchantAccount).get();
		if(m != null)
			return productService.findAll(m.getId());
		else {
			throw new Exception("Invalid User Credential");
		}
	}
	
	@JsonView(View.Summary.class)
	@Secured({"IS_AUTHENTICATED_ANONYMOUSLY"})
	@GetMapping(value = { "/list/","/"})
	public Iterable<Product> fetchAllProduct(){		
		return productService.findAll(); 
	}
	
	@JsonView(View.Summary.class)
	@Secured({"IS_AUTHENTICATED_ANONYMOUSLY"})
	@GetMapping("/{productId}")
	public Product fetchProduct(@PathVariable("productId") long productId){
		if(productService.get(productId).isPresent())
			return productService.get(productId).get();
		else
			return null;
		
	}
	
	@JsonView(View.Summary.class)
	@Secured({"IS_AUTHENTICATED_ANONYMOUSLY"})
	@GetMapping("/keyword/")
	public Page<Product> fetchProductByKeyword(
			@RequestParam(name = "page" , defaultValue = "0") int page,
			@RequestParam(name = "size" , defaultValue = "10") int size,
			@RequestParam("keyword") String keyword){
		return productService.search(page, size, keyword);
		
	}
	
	@JsonView(View.Summary.class)
	@Secured({"IS_AUTHENTICATED_ANONYMOUSLY"})
	@GetMapping("/category/")
	public Page<Product> fetchProductByCategory(
			@RequestParam(name = "page" , defaultValue = "0") int page,
			@RequestParam(name = "size" , defaultValue = "10") int size,
			@RequestParam("keyword") String category){
		return productService.searchByCategory(page, size, category);
		
	}
	
	@JsonView(View.Summary.class)
	@Secured({"IS_AUTHENTICATED_ANONYMOUSLY"})
	@GetMapping("/categoryById/{categoryId}")
	public Iterable<Product> fetchProductByCategoryId(
//			@RequestParam(name = "page" , defaultValue = "0") int page, 
//			@RequestParam(name = "size" , defaultValue = "10") int size,
			@PathVariable("categoryId") long categoryId){
		return productService.searchByCategoryId(categoryId);
		
	}
	
	@Secured(value = {"ROLE_MERCHANT", "ROLE_ADMIN"})
	@DeleteMapping("/{productId}")
	public boolean deleteProduct(@PathVariable("productId") long productId){
		return productService.delete(productId);
	}
	
	@JsonView(View.Summary.class)
	@Secured(value = {"ROLE_MERCHANT", "ROLE_ADMIN"})
	@PostMapping("/{merchantId}")
	public Product save(@Valid @RequestBody Product product, @PathVariable long merchantId) throws Exception{
		//System.err.println("Merchant found: ");
		Merchant m = userService.findMerchantById(merchantId).get();
		if(m == null) {
			throw new Exception("No registered merchant found with the parameter sent");
		}
		//System.err.println("Merchant found: "+m);
		product.setMerchant(m);
		
		//productService.save(product);
//		Optional<Product> newProduct = productService.get(product.getId());
//		rabbitTemplate.convertAndSend(newProduct);Optional<Product> newProduct = productService.get(product.getId());
//		rabbitTemplate.convertAndSend(newProduct);

		productService.save(product);
		rabbitMQSender.send(product);
		return product;
	}
	
	//fileupload features
	@Secured(value = {"ROLE_MERCHANT", "ROLE_ADMIN"})
	@PostMapping("/{productId}/uploadPhoto")
    public String uploadFile(@RequestParam("file") MultipartFile file, @PathVariable("productId") long productId) {
       Product p = productService.get(productId).get();
       if(p == null) {
    	   throw new ItemNotFoundException("Invalid product", productId);
       }
		
		String fileName = fileStorageService.storeFile(file, productId, true);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/productimages/")
                .path(fileName)
                .toUriString();
        if(fileDownloadUri == null) {
     	   throw new ItemNotFoundException("Upload Failed", productId); 
        }
        
        ProductImage pi = new ProductImage(fileDownloadUri);
        p.setImage(pi);
        productService.save(p);
        
        
        return fileDownloadUri;
    }


    
	
	
	
}
