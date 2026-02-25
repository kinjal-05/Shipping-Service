package com.shippingmanagement.controllers;

import com.shippingmanagement.dtos.ApiResponse;
import com.shippingmanagement.dtos.ShippingMethodRequest;
import com.shippingmanagement.dtos.ShippingMethodResponse;
import com.shippingmanagement.dtos.ShippingMethodUpdateRequest;
import com.shippingmanagement.models.ShippingMethod;
import com.shippingmanagement.services.ShippingMethodService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shipping-methods")
public class ShippingMethodController {

	@Autowired
	private final ShippingMethodService service;

	@Autowired
	public ShippingMethodController(ShippingMethodService service) {
		this.service = service;
	}

	@GetMapping
	public Page<ShippingMethod> getAllShippingMethods(
			@RequestParam(required = false) Boolean isActive,
			@PageableDefault(size = 10, sort = "id") Pageable pageable
	) {
		return service.getShippingMethods(isActive, pageable);
	}

	// ✅ Get by ID
	@GetMapping("/{id}")
	public ShippingMethod getShippingMethodById(@PathVariable Long id) {
		return service.getShippingMethodById(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ShippingMethodResponse createShippingMethod(
			@Valid @RequestBody ShippingMethodRequest request
	) {
		return service.createShippingMethod(request);
	}

	@PatchMapping("/{id}")
	public ShippingMethodResponse updateShippingMethod(
			@PathVariable Long id,
			@RequestBody ShippingMethodUpdateRequest request
	) {
		return service.updateShippingMethod(id, request);
	}

	@DeleteMapping("/{id}")
	public ApiResponse deleteShippingMethod(@PathVariable Long id) {

		service.deleteShippingMethod(id);

		return new ApiResponse(true, "Shipping method deleted successfully");
	}
}