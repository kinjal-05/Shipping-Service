package com.shippingmanagement.controllers;

import com.shippingmanagement.dtos.ApiResponse;
import com.shippingmanagement.dtos.ShipmentRequest;
import com.shippingmanagement.dtos.ShipmentResponse;
import com.shippingmanagement.services.ShipmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/shipments")

public class ShipmentController {

	@Autowired
	private final ShipmentService shipmentService;

	@Autowired
	public ShipmentController(ShipmentService shipmentService) {
		this.shipmentService = shipmentService;
	}

	// ✅ Create Shipment
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ShipmentResponse createShipment(
			@Valid @RequestBody ShipmentRequest request
	) {
		return shipmentService.createShipment(request);
	}

	@GetMapping
	public Page<ShipmentResponse> searchShipments(

			@RequestParam(required = false) Long id,
			@RequestParam(required = false) Long orderId,
			@RequestParam(required = false) Long userId,
			@RequestParam(required = false) String status,
			@RequestParam(required = false) Long shippingMethodId,

			@RequestParam(required = false)
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
			LocalDateTime startDate,

			@RequestParam(required = false)
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
			LocalDateTime endDate,

			@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC)
			Pageable pageable
	) {
		return shipmentService.searchShipments(
				id, orderId, userId, status, shippingMethodId,
				startDate, endDate, pageable
		);
	}

	@PatchMapping("/{id}/action")
	public ApiResponse updateShipmentStatus(
			@PathVariable Long id,
			@RequestParam String action
	) {

		shipmentService.updateShipmentStatus(id, action);

		return new ApiResponse(true, "Shipment " + action.toUpperCase() + " successfully");
	}
}