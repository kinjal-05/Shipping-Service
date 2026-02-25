package com.shippingmanagement.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "shipment_status_history",
		indexes = {
				@Index(name = "idx_shipment_id", columnList = "shipment_id"),
				@Index(name = "idx_status", columnList = "status")
		})

public class ShipmentStatusHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// 🔗 Foreign Key Mapping (Many history records -> One shipment)
	@NotNull(message = "Shipment is required")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "shipment_id", nullable = false)
	private Shipment shipment;

	@NotNull(message = "Status is required")
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false, length = 20)
	private ShippingStatus status;

	@NotNull(message = "Changed time is required")
	@PastOrPresent(message = "Changed time cannot be in the future")
	@Column(name = "changed_at", nullable = false)
	private LocalDateTime changedAt;

	@Size(max = 500, message = "Remarks cannot exceed 500 characters")
	@Column(name = "remarks", length = 500)
	private String remarks;

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	// Automatically set timestamps
	@PrePersist
	public void prePersist() {
		this.createdAt = LocalDateTime.now();

		// If changedAt not provided, set automatically
		if (this.changedAt == null) {
			this.changedAt = LocalDateTime.now();
		}
	}

	// Optional business validation
	@AssertTrue(message = "Changed time cannot be after current time")
	public boolean isValidChangedAt() {
		return changedAt == null || !changedAt.isAfter(LocalDateTime.now());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Shipment getShipment() {
		return shipment;
	}

	public void setShipment(Shipment shipment) {
		this.shipment = shipment;
	}

	public ShippingStatus getStatus() {
		return status;
	}

	public void setStatus(ShippingStatus status) {
		this.status = status;
	}

	public LocalDateTime getChangedAt() {
		return changedAt;
	}

	public void setChangedAt(LocalDateTime changedAt) {
		this.changedAt = changedAt;
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
}