package com.cts.capstone.repository;

import com.cts.capstone.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findAllByCategoryId(Long categoryId);
}
