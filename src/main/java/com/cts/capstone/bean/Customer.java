package com.cts.capstone.bean;

import java.util.Objects;

public class Customer {
	private String customerId;
	private String companyName;
	private String contactName;
	private String street;
	private String city;
	private String state;

	public Customer() {
	}

	public Customer(String customerId, String companyName, String contactName, String street, String city, String state) {
		this.customerId = customerId;
		this.companyName = companyName;
		this.contactName = contactName;
		this.street = street;
		this.city = city;
		this.state = state;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	@Override
	public int hashCode() {
		return Objects.hash(customerId, companyName, contactName, street, city, state);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Customer customer = (Customer) o;
		return Objects.equals(customerId, customer.customerId) && Objects.equals(companyName, customer.companyName) && Objects.equals(contactName, customer.contactName) && Objects.equals(street, customer.street) && Objects.equals(city, customer.city) && Objects.equals(state, customer.state);
	}

	@Override
	public String toString() {
		return "Customers{" +
				"customer_id='" + customerId + '\'' +
				", company_name='" + companyName + '\'' +
				", contact_name='" + contactName + '\'' +
				", street='" + street + '\'' +
				", cist='" + city + '\'' +
				", state='" + state + '\'' +
				'}';
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}