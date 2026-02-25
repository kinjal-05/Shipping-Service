package com.shippingmanagement.services;


import com.shippingmanagement.dtos.ShipmentRequest;
import com.shippingmanagement.dtos.ShipmentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface ShipmentService {

	ShipmentResponse createShipment(ShipmentRequest request);

	Page<ShipmentResponse> searchShipments(
			Long id,
			Long orderId,
			Long userId,
			String status,
			Long shippingMethodId,
			LocalDateTime startDate,
			LocalDateTime endDate,
			Pageable pageable
	);

	void updateShipmentStatus(Long id, String action);

}
