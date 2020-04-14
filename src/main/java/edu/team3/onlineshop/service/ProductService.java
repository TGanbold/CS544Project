package edu.team3.onlineshop.service;

import edu.team3.onlineshop.domain.Product;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ProductService {
	public Iterable<Product> getAll();
	public Optional<Product> get(long id);
	public Product save(Product product);
	public boolean delete(long id);
	public Page<Product> findAllbyPageAndSize(int page, int size);
	public Page<Product> search(int page, int size, String keyword);
	public Page<Product> searchByCategory(int page, int size, String category);
	public Iterable<Product> findAll();
	public Iterable<Product> findAll(long merchantId);
	public Iterable<Product> searchByCategoryId(long categoryId);
	
}
