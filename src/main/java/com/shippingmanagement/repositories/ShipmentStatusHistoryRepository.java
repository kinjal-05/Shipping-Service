package com.shippingmanagement.repositories;

import com.shippingmanagement.models.ShipmentStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ShipmentStatusHistoryRepository extends JpaRepository<ShipmentStatusHistory, Long>, JpaSpecificationExecutor<ShipmentStatusHistory> {

	List<ShipmentStatusHistory> findByShipmentId(Long shipmentId);
}
