package com.cts.capstone.controller;

import com.cts.capstone.exception.ProductNotFoundException;
import com.cts.capstone.model.Product;
import com.cts.capstone.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;


@RestController
@RequestMapping("product")
public class ProductController {

	private ProductService productService;

	private static Random random = new Random();

	public ProductController(ProductService productService) {
		super();
		this.productService = productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping
	public List<Product> getAllProducts() {
		return productService.findAll();
	}

	@GetMapping("/featured")
	public List<Product> getFeaturedProducts() {
		List<Product> productList = productService.findAll();
		HashSet<Product> featured = new HashSet<>();

		while (featured.size() < 5) {
			featured.add(productList.get(random.nextInt(productList.size())));
		}

		return new ArrayList<>(featured);
	}

	@GetMapping("{id}")
	public Product getProduct(@PathVariable Long id) {
		Product find = productService.findById(id);
		if (find == null) {
			throw new ProductNotFoundException(id);
		}
		return find;
	}

	@PostMapping()
	public ResponseEntity<Product> addProduct(@Valid @RequestBody Product product) {
		Product added = productService.add(product);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(added.getId()).toUri();
		return ResponseEntity.created(location).body(added);
	}

	@PutMapping
	public ResponseEntity<Product> putProduct(@Valid @RequestBody Product product) {
		Product added = productService.add(product);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Product> deleteProductById(@PathVariable Long id) {
		boolean delete = productService.delete(id);
		if (delete) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
