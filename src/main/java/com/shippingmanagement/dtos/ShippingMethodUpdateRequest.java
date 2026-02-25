package com.shippingmanagement.dtos;

public class ShippingMethodUpdateRequest {

	private String name;
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

	public ShippingMethodUpdateRequest(String name, String provider, Boolean isActive) {
		this.name = name;
		this.provider = provider;
		this.isActive = isActive;
	}

	public ShippingMethodUpdateRequest() {
	}
}