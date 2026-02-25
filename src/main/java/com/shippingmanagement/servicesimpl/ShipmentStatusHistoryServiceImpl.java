package com.shippingmanagement.servicesimpl;


import com.shippingmanagement.config.ShipmentStatusHistorySpecification;
import com.shippingmanagement.dtos.ShipmentStatusHistoryRequest;
import com.shippingmanagement.dtos.ShipmentStatusHistoryResponse;
import com.shippingmanagement.exception.ResourceNotFoundException;
import com.shippingmanagement.models.Shipment;
import com.shippingmanagement.models.ShipmentStatusHistory;
import com.shippingmanagement.models.ShippingStatus;
import com.shippingmanagement.repositories.ShipmentRepository;
import com.shippingmanagement.repositories.ShipmentStatusHistoryRepository;
import com.shippingmanagement.services.ShipmentStatusHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class ShipmentStatusHistoryServiceImpl implements ShipmentStatusHistoryService {

	@Autowired
	private final ShipmentRepository shipmentRepository;
	@Autowired
	private final ShipmentStatusHistoryRepository historyRepository;

	@Autowired
	public ShipmentStatusHistoryServiceImpl(ShipmentRepository shipmentRepository, ShipmentStatusHistoryRepository historyRepository) {
		this.shipmentRepository = shipmentRepository;
		this.historyRepository = historyRepository;
	}



	public ShipmentStatusHistory addStatusHistory(Long shipmentId, ShipmentStatusHistoryRequest request) {

		// Fetch Shipment
		Shipment shipment = shipmentRepository.findById(shipmentId)
				.orElseThrow(() -> new ResourceNotFoundException("Shipment not found with id: " + shipmentId));

		// Create history entity
		ShipmentStatusHistory history = new ShipmentStatusHistory();
		history.setShipment(shipment);
		history.setStatus(request.getStatus());
		history.setRemarks(request.getRemarks());

		// Optional changedAt
		if (request.getChangedAt() != null) {
			history.setChangedAt(request.getChangedAt());
		}

		return historyRepository.save(history);
	}

	@Override
	public Page<ShipmentStatusHistoryResponse> getStatusHistory(
			Long shipmentId,
			ShippingStatus status,
			java.time.LocalDateTime fromDate,
			java.time.LocalDateTime toDate,
			int page,
			int size) {

		// 🔍 Build Specification
		Specification<ShipmentStatusHistory> spec = Specification
				.where(ShipmentStatusHistorySpecification.hasShipmentId(shipmentId))
				.and(ShipmentStatusHistorySpecification.hasStatus(status))
				.and(ShipmentStatusHistorySpecification.changedAfter(fromDate))
				.and(ShipmentStatusHistorySpecification.changedBefore(toDate));

		// 📄 Pagination + Sorting
		Pageable pageable = PageRequest.of(page, size, Sort.by("changedAt").descending());

		Page<ShipmentStatusHistory> result = historyRepository.findAll(spec, pageable);

		// 🔁 Convert to DTO
		return result.map(this::mapToResponse);
	}

	private ShipmentStatusHistoryResponse mapToResponse(ShipmentStatusHistory history) {

		return new ShipmentStatusHistoryResponse(
				history.getId(),
				history.getShipment().getId(), // avoid lazy serialization issue
				history.getStatus().name(),
				history.getRemarks(),
				history.getChangedAt()
		);
	}
}