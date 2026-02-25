package com.shippingmanagement.dtos;

import java.time.LocalDateTime;

public class ShipmentStatusHistoryResponse {
	private Long id;
	private Long shipmentId;
	private String status;
	private String remarks;
	private LocalDateTime changedAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getShipmentId() {
		return shipmentId;
	}

	public void setShipmentId(Long shipmentId) {
		this.shipmentId = shipmentId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public LocalDateTime getChangedAt() {
		return changedAt;
	}

	public void setChangedAt(LocalDateTime changedAt) {
		this.changedAt = changedAt;
	}

	public ShipmentStatusHistoryResponse() {
	}

	public ShipmentStatusHistoryResponse(Long id, Long shipmentId, String status, String remarks, LocalDateTime changedAt) {
		this.id = id;
		this.shipmentId = shipmentId;
		this.status = status;
		this.remarks = remarks;
		this.changedAt = changedAt;
	}
}
