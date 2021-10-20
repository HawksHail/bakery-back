package com.cts.capstone.controller;

import com.cts.capstone.exception.ProductNotFoundException;
import com.cts.capstone.model.Product;
import com.cts.capstone.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("product")
@CrossOrigin(origins = "http://localhost:3000/")
public class ProductController {

	private ProductService productService;

	public ProductController(ProductService productService) {
		super();
		this.productService = productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping()
	public List<Product> getAllProducts() {
		return productService.findAll();
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

	@DeleteMapping
	public ResponseEntity<Product> deleteProduct(@Valid @RequestBody Product product) {
		productService.delete(product);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Product> deleteProductById(@PathVariable Long id) {
		productService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
