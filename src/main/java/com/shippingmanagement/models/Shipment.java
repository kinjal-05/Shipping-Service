package com.shippingmanagement.models;



import jakarta.persistence.*;
import jakarta.validation.constraints.*;


import java.time.LocalDateTime;

@Entity
@Table(name = "shipments",
		uniqueConstraints = {
				@UniqueConstraint(columnNames = "tracking_number")
		})

public class Shipment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "Order ID cannot be null")
	@Column(name = "order_id", nullable = false)
	private Long orderId;

	@NotNull(message = "User ID cannot be null")
	@Column(name = "user_id", nullable = false)
	private Long userId;

	@NotNull(message = "Shipping status is required")
	@Enumerated(EnumType.STRING)
	@Column(name = "shipping_status", nullable = false, length = 20)
	private ShippingStatus shippingStatus;

	@NotNull(message = "Shipping method is required")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "shipping_method_id", nullable = false)
	private ShippingMethod shippingMethod;


	@NotBlank(message = "Tracking number cannot be empty")
	@Size(max = 100, message = "Tracking number cannot exceed 100 characters")
	@Column(name = "tracking_number", nullable = false, unique = true, length = 100)
	private String trackingNumber;

	@NotNull(message = "Estimated delivery date is required")
	@Future(message = "Estimated delivery must be in the future")
	@Column(name = "estimated_delivery", nullable = false)
	private LocalDateTime estimatedDelivery;

	@Column(name = "actual_delivery")
	private LocalDateTime actualDelivery;

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@Size(max = 500, message = "Remarks cannot exceed 500 characters")
	@Column(name = "remarks", length = 500)
	private String remarks;

	// Automatically set timestamps
	@PrePersist
	public void prePersist() {
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	public void preUpdate() {
		this.updatedAt = LocalDateTime.now();
	}

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

	public ShippingMethod getShippingMethod() {
		return shippingMethod;
	}

	public void setShippingMethod(ShippingMethod shippingMethod) {
		this.shippingMethod = shippingMethod;
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Shipment(Long id, Long orderId, Long userId, ShippingStatus shippingStatus, ShippingMethod shippingMethod, String trackingNumber, LocalDateTime estimatedDelivery, LocalDateTime actualDelivery, LocalDateTime createdAt, LocalDateTime updatedAt, String remarks) {
		this.id = id;
		this.orderId = orderId;
		this.userId = userId;
		this.shippingStatus = shippingStatus;
		this.shippingMethod = shippingMethod;
		this.trackingNumber = trackingNumber;
		this.estimatedDelivery = estimatedDelivery;
		this.actualDelivery = actualDelivery;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.remarks = remarks;
	}

	public Shipment() {
	}
}
