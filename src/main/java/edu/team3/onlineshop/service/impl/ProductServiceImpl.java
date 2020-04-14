package edu.team3.onlineshop.service.impl;

import edu.team3.onlineshop.domain.Product;
import edu.team3.onlineshop.repository.ProductRepository;
import edu.team3.onlineshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	
	
	/**
	 * @param productRepository
	 */
	@Autowired
	public ProductServiceImpl(ProductRepository productRepository) {
		super();
		this.productRepository = productRepository;
	}

	@Override
	public Iterable<Product> getAll() {
		// TODO Auto-generated method stub
		return productRepository.findAll();
	}

	@Override
	public Optional<Product> get(long id) {
		// TODO Auto-generated method stub
		return productRepository.findById(id);
	}

	@Override
	public Product save(Product product) {
		// TODO Auto-generated method stub
		return productRepository.save(product);
	}

	@Override
	public boolean delete(long id) {
		productRepository.deleteById(id);
		return true;
	}

	@Override
	public Page<Product> search(int page, int size, String searchText) {
		if(page < 0) page = 0;
		if(size <= 0) size = 10;
		Pageable pageable = PageRequest.of(page , size, Sort.by("title"));
		return productRepository.findByTitleContaining(searchText, pageable);
	}

	@Override
	public Page<Product> findAllbyPageAndSize(int page, int size) {
		if(page < 0) page = 0;
		if(size <= 0) size = 10;
		Pageable pageable = PageRequest.of(page , size, Sort.by("title"));
		return this.productRepository.findAll(pageable);
	}

	@Override
	public Page<Product> searchByCategory(int page, int size, String category) {
		if(page < 0) page = 0;
		if(size <= 0) size = 10;
		Pageable pageable = PageRequest.of(page , size);
		return this.productRepository.findByCategory(category, pageable);
	}

	@Override
	public Iterable<Product> findAll() {
		return productRepository.findAll();
	}

	@Override
	public Iterable<Product> findAll(long merchantId) {
		
		return this.productRepository.findByMerchantId(merchantId);
	}

	@Override
	public Iterable<Product> searchByCategoryId(long categoryId) {
		// TODO Auto-generated method stub
		return productRepository.findByCategoryId(categoryId);
	}

}
