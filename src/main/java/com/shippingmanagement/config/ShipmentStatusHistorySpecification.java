package com.shippingmanagement.config;

import com.shippingmanagement.models.ShipmentStatusHistory;
import com.shippingmanagement.models.ShippingStatus;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class ShipmentStatusHistorySpecification {

	public static Specification<ShipmentStatusHistory> hasShipmentId(Long shipmentId) {
		return (root, query, cb) ->
				shipmentId == null ? null :
						cb.equal(root.get("shipment").get("id"), shipmentId);
	}

	public static Specification<ShipmentStatusHistory> hasStatus(ShippingStatus status) {
		return (root, query, cb) ->
				status == null ? null :
						cb.equal(root.get("status"), status);
	}

	public static Specification<ShipmentStatusHistory> changedAfter(LocalDateTime fromDate) {
		return (root, query, cb) ->
				fromDate == null ? null :
						cb.greaterThanOrEqualTo(root.get("changedAt"), fromDate);
	}

	public static Specification<ShipmentStatusHistory> changedBefore(LocalDateTime toDate) {
		return (root, query, cb) ->
				toDate == null ? null :
						cb.lessThanOrEqualTo(root.get("changedAt"), toDate);
	}
}