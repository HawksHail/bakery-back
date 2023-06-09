package com.cts.capstone.repository;

import com.cts.capstone.model.CartItem;
import com.cts.capstone.model.CartItemKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, CartItemKey> {

	List<CartItem> findAllByCustomerId(Long CustomerId);

	Optional<CartItem> findByCustomerIdAndProductId(Long customerId, Long productId);
}
