package com.hexaware.quitQ_backend_1.service.interf;

import com.hexaware.quitQ_backend_1.dto.AddressDto;
import com.hexaware.quitQ_backend_1.dto.Response;

public interface AddressService {
	Response saveAndUpdateAddress(AddressDto addressDto);
	Response getAddressById(Long id);
	Response deleteAddress(Long id);

}
