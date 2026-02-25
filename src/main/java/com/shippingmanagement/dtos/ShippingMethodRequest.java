package com.shippingmanagement.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ShippingMethodRequest {

	@NotBlank(message = "Name is required")
	@Size(max = 100)
	private String name;

	@NotBlank(message = "Provider is required")
	@Size(max = 150)
	private String provider;

	private Boolean isActive;

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

	public ShippingMethodRequest(String name, String provider, Boolean isActive) {
		this.name = name;
		this.provider = provider;
		this.isActive = isActive;
	}

	public ShippingMethodRequest() {
	}
}