package com.shippingmanagement.services;

import com.shippingmanagement.dtos.ShipmentStatusHistoryRequest;
import com.shippingmanagement.dtos.ShipmentStatusHistoryResponse;
import com.shippingmanagement.models.ShipmentStatusHistory;
import com.shippingmanagement.models.ShippingStatus;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

public interface ShipmentStatusHistoryService {
	ShipmentStatusHistory addStatusHistory(Long shipmentId, ShipmentStatusHistoryRequest request);
	Page<ShipmentStatusHistoryResponse> getStatusHistory(
			Long shipmentId,
			ShippingStatus status,
			LocalDateTime fromDate,
			LocalDateTime toDate,
			int page,
			int size);

}
