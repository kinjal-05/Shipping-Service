package com.shippingmanagement.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "shipping_methods",
		uniqueConstraints = {
				@UniqueConstraint(columnNames = {"name", "provider"})
		},
		indexes = {
				@Index(name = "idx_shipping_method_name", columnList = "name"),
				@Index(name = "idx_shipping_method_provider", columnList = "provider")
		})

public class ShippingMethod {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Shipping method name cannot be empty")
	@Size(max = 100, message = "Name cannot exceed 100 characters")
	@Column(name = "name", nullable = false, length = 100)
	private String name;

	@NotBlank(message = "Provider name cannot be empty")
	@Size(max = 150, message = "Provider cannot exceed 150 characters")
	@Column(name = "provider", nullable = false, length = 150)
	private String provider;

	@NotNull(message = "Active status must be specified")
	@Column(name = "is_active", nullable = false)
	private Boolean isActive = true;

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	// Automatically manage timestamps
	@PrePersist
	public void prePersist() {
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	public void preUpdate() {
		this.updatedAt = LocalDateTime.now();
	}

	// Optional business validation
	@AssertTrue(message = "Name must be uppercase (e.g., STANDARD, EXPRESS)")
	public boolean isNameUppercase() {
		return name == null || name.equals(name.toUpperCase());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public Boolean getActive() {
		return isActive;
	}

	public void setActive(Boolean active) {
		isActive = active;
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

	public ShippingMethod(Long id, String name, String provider, Boolean isActive, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.id = id;
		this.name = name;
		this.provider = provider;
		this.isActive = isActive;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public ShippingMethod() {
	}
}