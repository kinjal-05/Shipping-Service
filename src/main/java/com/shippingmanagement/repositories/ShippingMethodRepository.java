package com.shippingmanagement.repositories;

import com.shippingmanagement.models.ShippingMethod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShippingMethodRepository extends JpaRepository<ShippingMethod, Long> {

	Page<ShippingMethod> findByIsActive(Boolean isActive, Pageable pageable);
	Optional<ShippingMethod> findByNameAndProvider(String name, String provider);
}