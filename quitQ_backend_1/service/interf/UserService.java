package com.hexaware.quitQ_backend_1.service.interf;

import com.hexaware.quitQ_backend_1.dto.Response;
import com.hexaware.quitQ_backend_1.dto.SellerUpdateDTO;
import com.hexaware.quitQ_backend_1.dto.UserUpdateDTO;
import com.hexaware.quitQ_backend_1.entities.User;
import com.hexaware.quitQ_backend_1.request.LoginRequest;
import com.hexaware.quitQ_backend_1.request.RegisterUserRequest;

public interface UserService {
	Response registerUser(RegisterUserRequest registrationRequest);

	Response loginUser(LoginRequest loginRequest);

	Response getUserById(Long id);

	Response getAllUsers();

	User getLoginUser();

	Response updateUser(Long id, UserUpdateDTO dto);

	Response updateSeller(Long id, SellerUpdateDTO dto);

	Long getUserCount();

}
