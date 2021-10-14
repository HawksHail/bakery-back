package com.cts.capstone.service;

import com.cts.capstone.model.Supplier;
import com.cts.capstone.repository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SupplierService {

	private SupplierRepository supplierRepository;

	public SupplierService(SupplierRepository supplierRepository) {
		super();
		this.supplierRepository = supplierRepository;
	}

	public void setSupplierService(SupplierRepository categoryRepository) {
		this.supplierRepository = categoryRepository;
	}

	public List<Supplier> findAll() {
		return supplierRepository.findAll();
	}

	public Supplier findById(Long id) {
		return supplierRepository.findById(id).orElse(null);
	}

	public Supplier add(Supplier category) {
		return supplierRepository.save(category);
	}
}