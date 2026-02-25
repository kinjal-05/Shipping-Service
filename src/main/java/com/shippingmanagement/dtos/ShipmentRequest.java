package com.shippingmanagement.dtos;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class ShipmentRequest {

	@NotNull(message = "Order ID is required")
	private Long orderId;

	@NotNull(message = "User ID is required")
	private Long userId;

	@NotNull(message = "Shipping method ID is required")
	private Long shippingMethodId;

	@NotNull(message = "Estimated delivery is required")
	@Future(message = "Estimated delivery must be in the future")
	private LocalDateTime estimatedDelivery;

	@Size(max = 500)
	private String remarks;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getShippingMethodId() {
		return shippingMethodId;
	}

	public void setShippingMethodId(Long shippingMethodId) {
		this.shippingMethodId = shippingMethodId;
	}

	public LocalDateTime getEstimatedDelivery() {
		return estimatedDelivery;
	}

	public void setEstimatedDelivery(LocalDateTime estimatedDelivery) {
		this.estimatedDelivery = estimatedDelivery;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public ShipmentRequest(Long orderId, Long userId, Long shippingMethodId, LocalDateTime estimatedDelivery, String remarks) {
		this.orderId = orderId;
		this.userId = userId;
		this.shippingMethodId = shippingMethodId;
		this.estimatedDelivery = estimatedDelivery;
		this.remarks = remarks;
	}

	public ShipmentRequest() {
	}
}