package com.shippingmanagement.servicesimpl;

import com.shippingmanagement.dtos.ShippingMethodRequest;
import com.shippingmanagement.dtos.ShippingMethodResponse;
import com.shippingmanagement.dtos.ShippingMethodUpdateRequest;
import com.shippingmanagement.exception.DuplicateResourceException;
import com.shippingmanagement.exception.ResourceNotFoundException;
import com.shippingmanagement.models.ShippingMethod;
import com.shippingmanagement.repositories.ShippingMethodRepository;
import com.shippingmanagement.services.ShippingMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service

public class ShippingMethodServiceImpl implements ShippingMethodService {

	@Autowired
	private final ShippingMethodRepository repository;

	@Autowired
	public ShippingMethodServiceImpl(ShippingMethodRepository repository) {
		this.repository = repository;
	}

	@Override
	public Page<ShippingMethod> getShippingMethods(Boolean isActive, Pageable pageable) {

		if (isActive != null) {
			return repository.findByIsActive(isActive, pageable);
		}

		return repository.findAll(pageable);
	}

	@Override
	public ShippingMethod getShippingMethodById(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Shipping Method not found with id: " + id));
	}

	@Override
	public ShippingMethodResponse createShippingMethod(ShippingMethodRequest request) {


		repository.findByNameAndProvider(request.getName(), request.getProvider())
				.ifPresent(existing -> {
					throw new DuplicateResourceException("Shipping method already exists");
				});


		ShippingMethod method = new ShippingMethod();
		method.setName(request.getName().toUpperCase());
		method.setProvider(request.getProvider());
		method.setActive(request.getActive() != null ? request.getActive() : true);


		ShippingMethod saved = repository.save(method);


		return mapToResponse(saved);
	}

	private ShippingMethodResponse mapToResponse(ShippingMethod method) {

		ShippingMethodResponse response = new ShippingMethodResponse();

		response.setId(method.getId());
		response.setName(method.getName());
		response.setProvider(method.getProvider());
		response.setActive(method.getActive());
		response.setCreatedAt(method.getCreatedAt());
		response.setUpdatedAt(method.getUpdatedAt());

		return response;
	}

	@Override
	public ShippingMethodResponse updateShippingMethod(Long id, ShippingMethodUpdateRequest request) {

		// 🔍 Find existing record
		ShippingMethod method = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Shipping method not found with id: " + id));

		// 🛠 Update only provided fields (PATCH behavior)

		if (request.getName() != null) {
			method.setName(request.getName().toUpperCase());
		}

		if (request.getProvider() != null) {
			method.setProvider(request.getProvider());
		}

		if (request.getActive() != null) {
			method.setActive(request.getActive());
		}

		// 🔍 Check duplicate only if name or provider changed
		if (request.getName() != null || request.getProvider() != null) {

			String name = method.getName();
			String provider = method.getProvider();

			repository.findByNameAndProvider(name, provider)
					.ifPresent(existing -> {
						if (!existing.getId().equals(id)) {
							throw new DuplicateResourceException("Shipping method already exists");
						}
					});
		}

		// 💾 Save updated entity
		ShippingMethod updated = repository.save(method);

		return mapToResponse(updated);
	}

	@Override
	public void deleteShippingMethod(Long id) {

		// 🔍 Check if exists
		ShippingMethod method = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Shipping method not found with id: " + id));

		// ❌ Hard delete
		repository.delete(method);
	}



}