package com.shippingmanagement.controllers;

import com.shippingmanagement.dtos.ApiResponse;
import com.shippingmanagement.dtos.ApiResponse1;
import com.shippingmanagement.dtos.ShipmentStatusHistoryRequest;
import com.shippingmanagement.dtos.ShipmentStatusHistoryResponse;
import com.shippingmanagement.models.ShipmentStatusHistory;
import com.shippingmanagement.models.ShippingStatus;
import com.shippingmanagement.services.ShipmentStatusHistoryService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/shipments")
public class ShipmentStatusHistoryController {

	private final ShipmentStatusHistoryService service;

	public ShipmentStatusHistoryController(ShipmentStatusHistoryService service) {
		this.service = service;
	}

	@PostMapping("/{shipmentId}/status-history")
	public ResponseEntity<ApiResponse1> addStatusHistory(
			@PathVariable Long shipmentId,
			@Valid @RequestBody ShipmentStatusHistoryRequest request) {

		ShipmentStatusHistory history = service.addStatusHistory(shipmentId, request);

		return ResponseEntity.ok(
				new ApiResponse1("Status history added successfully", mapToResponse(history))
		);
	}
	private ShipmentStatusHistoryResponse mapToResponse(ShipmentStatusHistory history) {
		ShipmentStatusHistoryResponse response = new ShipmentStatusHistoryResponse();

		response.setId(history.getId());
		response.setShipmentId(history.getShipment().getId()); // only ID
		response.setStatus(history.getStatus().name());
		response.setRemarks(history.getRemarks());
		response.setChangedAt(history.getChangedAt());

		return response;
	}

	@GetMapping("/status-history")
	public Page<ShipmentStatusHistoryResponse> getStatusHistory(

			@RequestParam(required = false) Long shipmentId,

			@RequestParam(required = false) ShippingStatus status,

			@RequestParam(required = false)
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
			LocalDateTime fromDate,

			@RequestParam(required = false)
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
			LocalDateTime toDate,

			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size
	) {

		return service.getStatusHistory(
				shipmentId, status, fromDate, toDate, page, size
		);
	}
}