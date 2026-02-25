package com.shippingmanagement.config;

import com.shippingmanagement.models.Shipment;
import com.shippingmanagement.models.ShippingStatus;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class ShipmentSpecification {

	public static Specification<Shipment> filterShipments(
			Long id,
			Long orderId,
			Long userId,
			ShippingStatus status,
			Long shippingMethodId,
			LocalDateTime startDate,
			LocalDateTime endDate
	) {

		return (Root<Shipment> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

			Predicate predicate = cb.conjunction();

			if (id != null) {
				predicate = cb.and(predicate, cb.equal(root.get("id"), id));
			}

			if (orderId != null) {
				predicate = cb.and(predicate, cb.equal(root.get("orderId"), orderId));
			}

			if (userId != null) {
				predicate = cb.and(predicate, cb.equal(root.get("userId"), userId));
			}

			if (status != null) {
				predicate = cb.and(predicate, cb.equal(root.get("shippingStatus"), status));
			}

			if (shippingMethodId != null) {
				predicate = cb.and(predicate,
						cb.equal(root.get("shippingMethod").get("id"), shippingMethodId));
			}

			if (startDate != null) {
				predicate = cb.and(predicate,
						cb.greaterThanOrEqualTo(root.get("createdAt"), startDate));
			}

			if (endDate != null) {
				predicate = cb.and(predicate,
						cb.lessThanOrEqualTo(root.get("createdAt"), endDate));
			}

			return predicate;
		};
	}
}