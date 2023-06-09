package com.cts.capstone.controller;

import com.cts.capstone.exception.SupplierNotFoundException;
import com.cts.capstone.model.Supplier;
import com.cts.capstone.service.SupplierService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("supplier")
public class SupplierController {

	private SupplierService supplierService;

	public SupplierController(SupplierService supplierService) {
		super();
		this.supplierService = supplierService;
	}

	public SupplierService getSupplierService() {
		return supplierService;
	}

	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}

	@GetMapping()
	public List<Supplier> getAllSuppliers() {
		List<Supplier> all = supplierService.findAll();
		all.forEach(x -> x.setProductList(null));
		return all;
	}

	@GetMapping("{id}")
	public Supplier getSupplier(@PathVariable Long id) {
		Supplier find = supplierService.findById(id);
		if (find == null) {
			throw new SupplierNotFoundException(id);
		}
		return find;
	}

	@PostMapping()
	@PreAuthorize("hasAuthority('create:supplier')")
	public ResponseEntity<Supplier> addSupplier(@Valid @RequestBody Supplier supplier) {
		Supplier added = supplierService.add(supplier);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(added.getId()).toUri();
		return ResponseEntity.created(location).body(added);
	}

	@PutMapping
	@PreAuthorize("hasAuthority('update:supplier')")
	public ResponseEntity<Supplier> putSupplier(@Valid @RequestBody Supplier supplier) {
		Supplier added = supplierService.add(supplier);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("{id}")
	@PreAuthorize("hasAuthority('delete:supplier')")
	public ResponseEntity<Supplier> deleteSupplierById(@PathVariable Long id) {
		boolean delete = supplierService.delete(id);
		if (delete) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
