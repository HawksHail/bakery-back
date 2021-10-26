package com.cts.capstone.controller;

import com.cts.capstone.exception.ExceptionResponse;
import com.cts.capstone.exception.OrderDetailsNotFoundException;
import com.cts.capstone.model.OrderDetails;
import com.cts.capstone.model.OrderDetailsKey;
import com.cts.capstone.service.OrderDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("order/details")
//@CrossOrigin("*")
@CrossOrigin(originPatterns = {"https://master.ditbstqq8z8x3.amplifyapp.com/", "http://localhost:*"})
public class OrderDetailsController {

	private OrderDetailsService orderDetailsService;

	public OrderDetailsController(OrderDetailsService orderDetailsService) {
		super();
		this.orderDetailsService = orderDetailsService;
	}

	public void setOrderDetailsService(OrderDetailsService orderDetailsService) {
		this.orderDetailsService = orderDetailsService;
	}

	@GetMapping()
	public List<OrderDetails> getAllOrderDetails() {
		return orderDetailsService.findAll();
	}

	@PostMapping()
	public ResponseEntity<Object> addOrderDetailsList(@Valid @RequestBody List<OrderDetails> list) {
		if (list.size() < 1) {
			return ResponseEntity.badRequest().body(new ExceptionResponse(
					LocalDateTime.now(),
					HttpStatus.BAD_REQUEST,
					HttpStatus.BAD_REQUEST.getReasonPhrase(),
					"Empty list",
					"order/details"));
		}
		List<OrderDetails> added = orderDetailsService.addList(list);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(added.get(0).getId().getOrderId()).toUri();
		return ResponseEntity.created(location).body(added);
	}

	@GetMapping(value = "{orderId}/product/{productId}")
	public OrderDetails getOrderDetailsProduct(@PathVariable Long orderId, @PathVariable Long productId) {
		OrderDetails find = orderDetailsService.findByOrderIdAndProductId(orderId, productId);
		if (find == null) {
			throw new OrderDetailsNotFoundException(new OrderDetailsKey(orderId, productId));
		}
		return find;
	}

	@PutMapping
	public ResponseEntity<OrderDetails> putOrderDetails(@Valid @RequestBody OrderDetails orderDetails) {
		OrderDetails added = orderDetailsService.add(orderDetails);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("{orderId}/product/{productId}")
	public ResponseEntity<OrderDetails> deleteOrderDetails(@PathVariable Long orderId, @PathVariable Long productId) {
		OrderDetailsKey key = new OrderDetailsKey(orderId, productId);
		boolean delete = orderDetailsService.delete(key);
		if (delete) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}