package com.cts.capstone.service;

import com.cts.capstone.builder.CustomerBuilder;
import com.cts.capstone.builder.ProductBuilder;
import com.cts.capstone.model.*;
import com.cts.capstone.repository.CartItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CartItemServiceTest {

	@Mock
	CartItemRepository repository;

	CartItemService service;

	Customer customer;
	Product product;
	CartItem cartItem;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		service = new CartItemService(repository);
		customer = CustomerBuilder.of(1234L, "Cognizant", "Bob", "street", "city", "state");
		product = ProductBuilder.of(123L, "name", new Supplier(), new Category(), "765");
		cartItem = new CartItem(customer, product, 1);
	}

	@Test
	void setCartItemRepository() {
		service.setCartItemRepository(null);
		assertNull(service.getCartItemRepository());
	}

	@Test
	void findById() {
		when(repository.findByCustomerCustomerIdAndProductId(anyLong(), anyLong()))
				.thenReturn(java.util.Optional.of(cartItem));

		CartItem actual = service.findById(1234L, 123L);

		assertEquals(cartItem, actual);
		verify(repository).findByCustomerCustomerIdAndProductId(anyLong(), anyLong());
	}

	@Test
	void findByIdNotFound() {
		when(repository.findByCustomerCustomerIdAndProductId(anyLong(), anyLong()))
				.thenReturn(java.util.Optional.empty());

		CartItem actual = service.findById(1234L, 123L);

		assertNull(actual);
		verify(repository).findByCustomerCustomerIdAndProductId(anyLong(), anyLong());
	}

	@Test
	void add() {
		when(repository.findByCustomerCustomerIdAndProductId(anyLong(), anyLong()))
				.thenReturn(java.util.Optional.of(cartItem));

		service.add(customer, product);

		assertEquals(2, cartItem.getQuantity());
		verify(repository).save(any(CartItem.class));
	}

	@Test
	void addNewItem() {
		when(repository.findByCustomerCustomerIdAndProductId(anyLong(), anyLong()))
				.thenReturn(java.util.Optional.empty());

		service.add(customer, product);

		assertEquals(1, cartItem.getQuantity());
		verify(repository).save(any(CartItem.class));
	}

	@Test
	void remove() {
		when(repository.findByCustomerCustomerIdAndProductId(anyLong(), anyLong()))
				.thenReturn(java.util.Optional.of(cartItem));

		service.remove(customer, product);

		assertEquals(0, cartItem.getQuantity());
		verify(repository).delete(any(CartItem.class));
	}

	@Test
	void removeDecrementOnly() {
		cartItem.setQuantity(2);
		when(repository.findByCustomerCustomerIdAndProductId(anyLong(), anyLong()))
				.thenReturn(java.util.Optional.of(cartItem));

		service.remove(customer, product);

		assertEquals(1, cartItem.getQuantity());
		verify(repository).save(any(CartItem.class));
	}

	@Test
	void clear() {
		when(repository.findAllByCustomerCustomerId(anyLong()))
				.thenReturn(List.of(cartItem));

		service.clear(customer.getCustomerId());

		verify(repository).findAllByCustomerCustomerId(anyLong());
		verify(repository).deleteAllInBatch(anyList());
	}

	@Test
	void findAllByCustomerId() {
		when(repository.findAllByCustomerCustomerId(anyLong()))
				.thenReturn(List.of(cartItem));

		service.findAllByCustomerId(customer.getCustomerId());

		verify(repository).findAllByCustomerCustomerId(anyLong());
	}
}