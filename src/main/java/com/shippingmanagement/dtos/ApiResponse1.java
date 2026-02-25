package com.shippingmanagement.dtos;

public class ApiResponse1 {
	private String message;
	private Object data;

	public ApiResponse1(String message, Object data) {
		this.message = message;
		this.data = data;
	}

	public String getMessage() { return message; }
	public Object getData() { return data; }
}
