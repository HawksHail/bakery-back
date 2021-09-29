package com.cts.capstone.builder;

import com.cts.capstone.model.Order;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderBuilder {

	List<Order> list;

	public OrderBuilder() {
		list = new ArrayList<>();
	}

	public static Order of() {
		return new Order();
	}

	public OrderBuilder w(long orderId, String customerId) {
		list.add(of(orderId, customerId));
		return this;
	}

	public static Order of(long orderId, String customerId) {
		return new Order(orderId, customerId);
	}

	public OrderBuilder w(long orderId, String customerId, int year, int month, int day) {
		list.add(of(orderId, customerId, year, month, day));
		return this;
	}

	public static Order of(long orderId, String customerId, int year, int month, int day) {
		return new Order(orderId, customerId, LocalDate.of(year, month, day));
	}

	public List<Order> build() {
		return this.list;
	}
}