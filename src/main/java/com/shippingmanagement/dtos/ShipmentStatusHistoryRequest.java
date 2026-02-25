package com.shippingmanagement.dtos;

import com.shippingmanagement.models.ShippingStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class ShipmentStatusHistoryRequest {

	@NotNull(message = "Status is required")
	private ShippingStatus status;

	private LocalDateTime changedAt;

	@Size(max = 500, message = "Remarks cannot exceed 500 characters")
	private String remarks;

	// Getters & Setters
	public ShippingStatus getStatus() { return status; }
	public void setStatus(ShippingStatus status) { this.status = status; }

	public LocalDateTime getChangedAt() { return changedAt; }
	public void setChangedAt(LocalDateTime changedAt) { this.changedAt = changedAt; }

	public String getRemarks() { return remarks; }
	public void setRemarks(String remarks) { this.remarks = remarks; }
}
