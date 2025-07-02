package com.hexaware.quitQ_backend_1.service.implementation;

import org.springframework.stereotype.Service;

import com.hexaware.quitQ_backend_1.dto.AddressDto;
import com.hexaware.quitQ_backend_1.dto.Response;
import com.hexaware.quitQ_backend_1.entities.Address;
import com.hexaware.quitQ_backend_1.entities.User;
import com.hexaware.quitQ_backend_1.mapper.EntityDtoMapper;
import com.hexaware.quitQ_backend_1.repository.AddressRepository;
import com.hexaware.quitQ_backend_1.service.interf.AddressService;
import com.hexaware.quitQ_backend_1.service.interf.UserService;

@Service
public class AddressServiceImpl implements AddressService {

	private final AddressRepository addressRepository;
	private final UserService userService;

	public AddressServiceImpl(AddressRepository addressRepository, UserService userService) {
		super();
		this.addressRepository = addressRepository;
		this.userService = userService;
	}

	@Override
	public Response saveAndUpdateAddress(AddressDto addressDto) {
	    User user = userService.getLoginUser();
	    Address address = user.getAddress();

	    boolean isNewAddress = false;

	    
	    if (address == null) {
	        address = new Address();
	        address.setUser(user);
	        isNewAddress = true;
	    }
	    
	    if (addressDto.getStreet() != null)
	        address.setStreet(addressDto.getStreet());
	    if (addressDto.getCity() != null)
	        address.setCity(addressDto.getCity());
	    if (addressDto.getState() != null)
	        address.setState(addressDto.getState());
	    if (addressDto.getZipCode() != null)
	        address.setZipCode(addressDto.getZipCode());
	    if (addressDto.getCountry() != null)
	        address.setCountry(addressDto.getCountry());
	    if (addressDto.getAddressType() != null)
	        address.setAddressType(addressDto.getAddressType());

	    
	    addressRepository.save(address);

	    String message = isNewAddress ? "Address successfully created" : "Address successfully updated";

	    Response response = new Response();
	    response.setStatus(200);
	    response.setMessage(message);
	    response.setAddress(EntityDtoMapper.toAddressDto(address));

	    return response;
	}


	@Override
	public Response getAddressById(Long id) {
		// TODO Auto-generated method stub
		Address address = addressRepository.findById(id).orElseThrow(() -> new RuntimeException("Address not found"));

		Response response = new Response();
		response.setStatus(200);
		response.setMessage("Address found");
		response.setAddress(EntityDtoMapper.toAddressDto(address));

		return response;

	}

	@Override
	public Response deleteAddress(Long id) {
		// TODO Auto-generated method stub
		Response response = new Response();
		if (addressRepository.existsById(id)) {
			addressRepository.deleteById(id);
			response.setStatus(200);
			response.setMessage("Address deleted successfully");
		} else {
			response.setStatus(404);
			response.setMessage("Address not found");
		}
		return response;
	}

}
