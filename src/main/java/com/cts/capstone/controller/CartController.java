package com.cts.capstone.controller;

import com.cts.capstone.model.Order;
import com.cts.capstone.model.OrderDetails;
import com.cts.capstone.service.DbService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CartController {

	@Autowired
	DbService service;
	@Autowired
	private Gson gson;

	public CartController() {
	}

	public CartController(DbService service) {
		this.service = service;
	}

	public void setGson(Gson gson) {
		this.gson = gson;
	}

	/**
	 * Get all customers
	 *
	 * @return Json of all customers
	 */
	@GetMapping(value = "customer", produces = "application/json")
	public String getCustomer() {
		return gson.toJson(service.getAllCustomers());
	}

	/**
	 * Get customer with specified ID
	 *
	 * @param id Customer ID to retrieve
	 * @return Json of specified customer
	 */
	@GetMapping(value = "customer/{id}", produces = "application/json")
	public String getCustomer(@PathVariable String id) {
		return gson.toJson(service.getCustomer(id));
	}

	/**
	 * Get all orders
	 *
	 * @return Json of all orders
	 */
	@GetMapping(value = "order", produces = "application/json")
	public String getOrder() {
		return gson.toJson(service.getAllOrders());
	}

	/**
	 * Create an order
	 *
	 * @param order order to be added
	 * @return order if successful, else null
	 */
	@PostMapping(value = "order")
	public Order createOrder(@RequestBody Order order) {
		if (!service.createOrder(order)) {
			return null;
		}
		return order;
	}

	/**
	 * Update an order
	 *
	 * @param order order to be updated
	 * @return order if successful, else null
	 */
	@PatchMapping(value = "order")
	public Order updateOrder(@RequestBody Order order) {
		if (!service.updateOrder(order)) {
			return null;
		}
		return order;
	}

	/**
	 * Get order with specified ID
	 *
	 * @param id Order ID to be retrieved
	 * @return Json of specified order
	 */
	@GetMapping(value = "order/{id}", produces = "application/json")
	public String getOrder(@PathVariable Long id) {
		return gson.toJson(service.getOrder(id));
	}

	/**
	 * Get all order details
	 *
	 * @return Json of all order details
	 */
	@GetMapping(value = "order/details", produces = "application/json")
	public String getOrderDetails() {
		return gson.toJson(service.getAllOrderDetails());
	}

	/**
	 * Get all order details for specified order ID
	 *
	 * @param id Order details ID to retrieve
	 * @return Json of all order details for specified order
	 */
	@GetMapping(value = "order/details/{id}", produces = "application/json")
	public String getOrderDetails(@PathVariable Long id) {
		return gson.toJson(service.getOrderDetails(id));
	}

	/**
	 * Get all order details for specified order ID
	 *
	 * @param orderId   Order ID to retrieve
	 * @param productId product ID to retrieve
	 * @return Json of all order details for specified order
	 */
	@GetMapping(value = "order/details/{orderId}/product/{productId}", produces = "application/json")
	public String getOrderDetailsProduct(@PathVariable Long orderId, @PathVariable Long productId) {
		return gson.toJson(service.getOrderDetails(orderId, productId));
	}

	/**
	 * Create order details with an array
	 *
	 * @param details array of details to create
	 * @return details if successful, else null
	 */
	@PostMapping(value = "order/details")
	public OrderDetails[] createOrderDetails(@RequestBody OrderDetails[] details) {
		if (!service.createOrderDetailsList(details)) {
			return null;
		}
		return details;
	}

	/**
	 * Update an order details
	 *
	 * @param details details to update
	 * @return details if successful, else null
	 */
	@PatchMapping(value = "order/details")
	public OrderDetails updateOrderDetails(@RequestBody OrderDetails details) {
		if (!service.updateOrderDetails(details)) {
			return null;
		}
		return details;
	}

	/**
	 * Get all orders for a specific user
	 *
	 * @param customerId user to get orders for
	 * @return list of orders from specified user
	 */
	@GetMapping(value = "order/customer/{customerId}", produces = "application/json")
	public String getCustomerOrders(@PathVariable String customerId) {
		List<Order> list = service.getOrdersForCustomer(customerId);
		return gson.toJson(list);
	}

	/**
	 * Get all orderDetails for a specified order
	 *
	 * @param orderId order to get details for
	 * @return list of orderDetails for specified order
	 */
	@GetMapping(value = "order/{orderId}/details", produces = "application/json")
	public String getOrderDetailsForOrder(@PathVariable Long orderId) {
		List<OrderDetails> list = service.getDetailsForOrder(orderId);
		return gson.toJson(list);
	}

}
