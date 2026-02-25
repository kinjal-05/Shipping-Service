package com.shippingmanagement.servicesimpl;

import com.shippingmanagement.config.ShipmentSpecification;
import com.shippingmanagement.dtos.ShipmentAction;
import com.shippingmanagement.dtos.ShipmentRequest;
import com.shippingmanagement.dtos.ShipmentResponse;
import com.shippingmanagement.exception.ResourceNotFoundException;
import com.shippingmanagement.models.Shipment;
import com.shippingmanagement.models.ShippingMethod;
import com.shippingmanagement.models.ShippingStatus;
import com.shippingmanagement.repositories.ShipmentRepository;
import com.shippingmanagement.repositories.ShippingMethodRepository;
import com.shippingmanagement.services.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ShipmentServiceImpl implements ShipmentService {

	@Autowired
	private final ShipmentRepository shipmentRepository;
	@Autowired
	private final ShippingMethodRepository shippingMethodRepository;

	@Autowired
	public ShipmentServiceImpl(ShipmentRepository shipmentRepository, ShippingMethodRepository shippingMethodRepository) {
		this.shipmentRepository = shipmentRepository;
		this.shippingMethodRepository = shippingMethodRepository;
	}

	@Override
	public ShipmentResponse createShipment(ShipmentRequest request) {

		// 🔍 Validate Shipping Method
		ShippingMethod shippingMethod = shippingMethodRepository.findById(request.getShippingMethodId())
				.orElseThrow(() -> new ResourceNotFoundException("Shipping method not found"));

		// 🔐 Generate unique tracking number
		String trackingNumber = generateTrackingNumber();

		// 🏗 Create Shipment
		Shipment shipment = new Shipment();
		shipment.setOrderId(request.getOrderId());
		shipment.setUserId(request.getUserId());
		shipment.setShippingMethod(shippingMethod);
		shipment.setTrackingNumber(trackingNumber);
		shipment.setEstimatedDelivery(request.getEstimatedDelivery());
		shipment.setRemarks(request.getRemarks());

		// Default status
		shipment.setShippingStatus(ShippingStatus.PENDING);

		// 💾 Save
		Shipment saved = shipmentRepository.save(shipment);

		// 🔁 Map response
		return mapToResponse(saved);
	}

	private String generateTrackingNumber() {
		return "TRK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
	}

	private ShipmentResponse mapToResponse(Shipment shipment) {

		ShipmentResponse response = new ShipmentResponse();

		response.setId(shipment.getId());
		response.setOrderId(shipment.getOrderId());
		response.setUserId(shipment.getUserId());
		response.setShippingStatus(shipment.getShippingStatus());
		response.setShippingMethodId(shipment.getShippingMethod().getId());
		response.setTrackingNumber(shipment.getTrackingNumber());
		response.setEstimatedDelivery(shipment.getEstimatedDelivery());
		response.setActualDelivery(shipment.getActualDelivery());
		response.setRemarks(shipment.getRemarks());
		response.setCreatedAt(shipment.getCreatedAt());
		response.setUpdatedAt(shipment.getUpdatedAt());

		return response;
	}

	@Override
	public Page<ShipmentResponse> searchShipments(
			Long id,
			Long orderId,
			Long userId,
			String status,
			Long shippingMethodId,
			LocalDateTime startDate,
			LocalDateTime endDate,
			Pageable pageable
	) {

		ShippingStatus shippingStatus = null;

		if (status != null) {
			try {
				shippingStatus = ShippingStatus.valueOf(status.toUpperCase());
			} catch (Exception e) {
				throw new IllegalArgumentException("Invalid shipping status");
			}
		}

		Specification<Shipment> spec = ShipmentSpecification.filterShipments(
				id, orderId, userId, shippingStatus, shippingMethodId, startDate, endDate
		);

		Page<Shipment> page = shipmentRepository.findAll(spec, pageable);

		if (id != null && page.isEmpty()) {
			throw new ResourceNotFoundException("Shipment not found with id: " + id);
		}

		return page.map(this::mapToResponse);
	}

	@Override
	public void updateShipmentStatus(Long id, String action) {

		// 🔍 Fetch Shipment
		Shipment shipment = shipmentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Shipment not found with id: " + id));

		ShipmentAction shipmentAction;

		try {
			shipmentAction = ShipmentAction.valueOf(action.toUpperCase());
		} catch (Exception e) {
			throw new IllegalArgumentException("Invalid action. Allowed values: CANCEL, DELIVER");
		}

		switch (shipmentAction) {

			case CANCEL:
				if (shipment.getShippingStatus() == ShippingStatus.DELIVERED) {
					throw new IllegalStateException("Delivered shipment cannot be cancelled");
				}
				shipment.setShippingStatus(ShippingStatus.CANCELLED);
				break;

			case DELIVER:
				if (shipment.getShippingStatus() == ShippingStatus.CANCELLED) {
					throw new IllegalStateException("Cancelled shipment cannot be delivered");
				}
				shipment.setShippingStatus(ShippingStatus.DELIVERED);
				shipment.setActualDelivery(LocalDateTime.now());
				break;
		}

		shipmentRepository.save(shipment);
	}
}
