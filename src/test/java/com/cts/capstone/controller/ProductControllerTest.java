package com.cts.capstone.controller;

import com.cts.capstone.builder.ProductBuilder;
import com.cts.capstone.exception.ProductNotFoundException;
import com.cts.capstone.model.Product;
import com.cts.capstone.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductControllerTest {

	@Mock
	ProductService service;

	ProductController controller;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		controller = new ProductController(service);
	}

	@Test
	void setProductService() {
		controller.setProductService(null);
		assertNull(controller.getProductService());
	}

	@Test
	void getAllProducts() {
		List<Product> expected = new ProductBuilder()
				.w(123, "name", "123")
				.w(124, "name2", "321")
				.build();
		when(service.findAll())
				.thenReturn(expected);

		List<Product> actual = controller.getAllProducts();

		assertEquals(expected, actual);
		verify(service, times(1)).findAll();
	}

	@Test
	void getFeaturedProducts() {
		List<Product> expected = new ProductBuilder()
				.w(123, "name1", "123")
				.w(124, "name2", "321")
				.w(124, "name3", "321")
				.w(124, "name4", "321")
				.w(124, "name5", "321")
				.w(124, "name6", "321")
				.w(124, "name7", "321")
				.build();
		when(service.findAll())
				.thenReturn(expected);

		List<Product> actual = controller.getFeaturedProducts();

		assertEquals(5, actual.size());
		verify(service, times(1)).findAll();
	}

	@Test
	void getProduct() {
		Product expected = ProductBuilder.of(123, "name", "123");
		when(service.findById(anyLong()))
				.thenReturn(expected);

		Product actual = controller.getProduct(123L);

		assertEquals(expected, actual);
		verify(service, times(1)).findById(anyLong());
	}

	@Test
	void getProductNotFound() {
		Product expected = ProductBuilder.of(123, "name", "123");
		when(service.findById(anyLong()))
				.thenReturn(null);

		assertThrows(ProductNotFoundException.class, () -> controller.getProduct(123L));

		verify(service, times(1)).findById(anyLong());
	}

	@Test
	void addProduct() {
		Product expected = ProductBuilder.of(123, "name", "123");
		when(service.add(any(Product.class)))
				.thenReturn(expected);

		ResponseEntity<Product> actual = controller.addProduct(expected);

		assertEquals(HttpStatus.CREATED, actual.getStatusCode());
		assertEquals(expected, actual.getBody());
		assertTrue(Objects.requireNonNull(actual.getHeaders().get("Location")).get(0).contains(String.valueOf(expected.getId())));
		verify(service, times(1)).add(any(Product.class));
	}

	@Test
	void putProduct() {
		Product expected = ProductBuilder.of(123, "name", "123");
		when(service.add(any(Product.class)))
				.thenReturn(expected);

		ResponseEntity<Product> actual = controller.putProduct(expected);

		assertEquals(HttpStatus.NO_CONTENT, actual.getStatusCode());
		verify(service, times(1)).add(any(Product.class));
	}

	@Test
	void deleteProductById() {
		Product expected = ProductBuilder.of(123, "name", "123");
		when(service.delete(anyLong()))
				.thenReturn(true);

		ResponseEntity<Product> actual = controller.deleteProductById(123L);

		assertEquals(HttpStatus.NO_CONTENT, actual.getStatusCode());
		verify(service, times(1)).delete(anyLong());
	}

	@Test
	void deleteProductByIdNotFound() {
		Product expected = ProductBuilder.of(123, "name", "123");
		when(service.delete(anyLong()))
				.thenReturn(false);

		ResponseEntity<Product> actual = controller.deleteProductById(123L);

		assertEquals(HttpStatus.NOT_FOUND, actual.getStatusCode());
		verify(service, times(1)).delete(anyLong());
	}
}