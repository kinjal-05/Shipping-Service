package com.shippingmanagement.dtos;

import com.shippingmanagement.models.ShippingStatus;

import java.time.LocalDateTime;

public class ShipmentResponse {

	private Long id;
	private Long orderId;
	private Long userId;
	private ShippingStatus shippingStatus;
	private Long shippingMethodId;
	private String trackingNumber;
	private LocalDateTime estimatedDelivery;
	private LocalDateTime actualDelivery;
	private String remarks;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public ShippingStatus getShippingStatus() {
		return shippingStatus;
	}

	public void setShippingStatus(ShippingStatus shippingStatus) {
		this.shippingStatus = shippingStatus;
	}

	public Long getShippingMethodId() {
		return shippingMethodId;
	}

	public void setShippingMethodId(Long shippingMethodId) {
		this.shippingMethodId = shippingMethodId;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public LocalDateTime getEstimatedDelivery() {
		return estimatedDelivery;
	}

	public void setEstimatedDelivery(LocalDateTime estimatedDelivery) {
		this.estimatedDelivery = estimatedDelivery;
	}

	public LocalDateTime getActualDelivery() {
		return actualDelivery;
	}

	public void setActualDelivery(LocalDateTime actualDelivery) {
		this.actualDelivery = actualDelivery;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public ShipmentResponse(Long id, Long orderId, Long userId, ShippingStatus shippingStatus, Long shippingMethodId, String trackingNumber, LocalDateTime estimatedDelivery, LocalDateTime actualDelivery, String remarks, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.id = id;
		this.orderId = orderId;
		this.userId = userId;
		this.shippingStatus = shippingStatus;
		this.shippingMethodId = shippingMethodId;
		this.trackingNumber = trackingNumber;
		this.estimatedDelivery = estimatedDelivery;
		this.actualDelivery = actualDelivery;
		this.remarks = remarks;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public ShipmentResponse() {
	}
}
