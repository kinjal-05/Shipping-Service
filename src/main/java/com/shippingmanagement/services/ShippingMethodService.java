package com.shippingmanagement.services;

import com.shippingmanagement.dtos.ShippingMethodRequest;
import com.shippingmanagement.dtos.ShippingMethodResponse;
import com.shippingmanagement.dtos.ShippingMethodUpdateRequest;
import com.shippingmanagement.models.ShippingMethod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ShippingMethodService {

	Page<ShippingMethod> getShippingMethods(Boolean isActive, Pageable pageable);
	ShippingMethodResponse createShippingMethod(ShippingMethodRequest request);
	ShippingMethod getShippingMethodById(Long id);
	ShippingMethodResponse updateShippingMethod(Long id, ShippingMethodUpdateRequest request);
	void deleteShippingMethod(Long id);


}