package com.hexaware.quitQ_backend_1.service.implementation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hexaware.quitQ_backend_1.dto.Response;
import com.hexaware.quitQ_backend_1.dto.SellerUpdateDTO;
import com.hexaware.quitQ_backend_1.dto.UserDto;
import com.hexaware.quitQ_backend_1.dto.UserUpdateDTO;
import com.hexaware.quitQ_backend_1.entities.User;
import com.hexaware.quitQ_backend_1.enums.Role;
import com.hexaware.quitQ_backend_1.exception.InvalidCredentialsException;
import com.hexaware.quitQ_backend_1.exception.ResourceNotFoundException;
import com.hexaware.quitQ_backend_1.mapper.EntityDtoMapper;
import com.hexaware.quitQ_backend_1.repository.UserRepository;
import com.hexaware.quitQ_backend_1.request.LoginRequest;
import com.hexaware.quitQ_backend_1.request.RegisterUserRequest;
import com.hexaware.quitQ_backend_1.security.JwtUtils;
import com.hexaware.quitQ_backend_1.service.interf.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	private final UserRepository userRepository;
	private final JwtUtils jwtUtils;
	private final EntityDtoMapper entityDtoMapper;
	private final PasswordEncoder passwordEncoder;
	

	public UserServiceImpl(UserRepository userRepository, JwtUtils jwtUtils, EntityDtoMapper entityDtoMapper,
			PasswordEncoder passwordEncoder) {
		super();
		this.userRepository = userRepository;
		this.jwtUtils = jwtUtils;
		this.entityDtoMapper = entityDtoMapper;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Response registerUser(RegisterUserRequest registrationRequest) {
		// TODO Auto-generated method stub
		if(userRepository.findByEmail(registrationRequest.getEmail()).isPresent()) {
			throw new ResourceNotFoundException("Email is already registered");
		}
		
		Role role= Role.USER;
		
		if (registrationRequest.getRole() != null && registrationRequest.getRole().equals(Role.ADMIN)) {
            role = Role.ADMIN;
        }
		if (registrationRequest.getRole() != null && registrationRequest.getRole().equals(Role.SELLER)) {
            role = Role.SELLER;
        }
		
		User user = new User();
		    user.setName(registrationRequest.getName());
		    user.setEmail(registrationRequest.getEmail());
		    user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
		    user.setPhoneNumber(registrationRequest.getPhoneNumber());
		    user.setGender(registrationRequest.getGender());
		    user.setRole(registrationRequest.getRole());
		    user.setRegistrationDate(LocalDateTime.now());
		    
		    if (role == Role.SELLER) {
		        user.setShopName(registrationRequest.getShopName());
		        user.setBusinessLicense(registrationRequest.getBusinessLicense());
		        user.setGstNumber(registrationRequest.getGstNumber());
		        user.setShopAddress(registrationRequest.getShopAddress());
		    }
		    
		 User savedUser = userRepository.save(user);
		 
		 UserDto userDto = EntityDtoMapper.toUserDto(savedUser);
		 
		 Response response = new Response();
		    response.setStatus(200);
		    response.setMessage("User successfully registered");
		    response.setUser(userDto);
		    response.setRole(savedUser.getRole().name());
		
		return response;
	}

		@Override
		public Response loginUser(LoginRequest loginRequest) {
			// TODO Auto-generated method stub
			
			User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(()-> new ResourceNotFoundException("Email not found"));
			
			if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
				throw new InvalidCredentialsException("Password does not match!");
			}
		
			String token = jwtUtils.generateToken(user);
			
		
			Response response = new Response();
			response.setStatus(200);
			response.setMessage("User Successfully Logged In");
			response.setToken(token);
			response.setExpirationTime("30 days");
			response.setRole(user.getRole().name());
			response.setUser(EntityDtoMapper.toUserDto(user));
			response.setUserId(user.getId());
	
			
			return response;
		}

	@Override
	public Response getUserById(Long id) {
		// TODO Auto-generated method stub
		User user = userRepository.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));

		UserDto userDto = EntityDtoMapper.toUserDto(user);
	    
	    Response response = new Response();
	    response.setStatus(200);
	    response.setMessage("User fetched successfully");
	    response.setUser(userDto);

	    return response;

	}

	@Override
	public Response getAllUsers() {
		// TODO Auto-generated method stub
		List<User> users = userRepository.findAll();

	    List<UserDto> userDtoList = new ArrayList<>();
	    for (User user : users) {
	        UserDto userDto = EntityDtoMapper.toUserDto(user);
	        userDtoList.add(userDto);
	    }

	    Response response = new Response();
	    response.setStatus(200);
	    response.setMessage("All users fetched successfully");
	    response.setUserList(userDtoList);
	 

	    return response;
	}

	@Override
	public User getLoginUser() {
		// TODO Auto-generated method stub
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        String  email = authentication.getName();
	        System.out.println("User Email is: " + email);
	        return userRepository.findByEmail(email)
	                .orElseThrow(()-> new UsernameNotFoundException("User Not found"));
		
	}

	@Override
	public Response updateUser(Long id, UserUpdateDTO dto) {
		// TODO Auto-generated method stub
		User user = userRepository.findById(id)
		        .orElseThrow(() -> new RuntimeException("User not found"));

		    user.setName(dto.getName());
		    user.setGender(dto.getGender());
		    user.setPhoneNumber(dto.getPhoneNumber());

		    userRepository.save(user);
		    Response response = new Response();
		    response.setStatus(200);
		    response.setMessage("User updated successfully");
		    response.setUserUpdateDTO(dto);
		    return response;
	}

	@Override
	public Response updateSeller(Long id, SellerUpdateDTO dto) {
		// TODO Auto-generated method stub
		User user = userRepository.findById(id)
		        .orElseThrow(() -> new RuntimeException("User not found"));

		    if (user.getRole() != Role.SELLER) {
		        throw new RuntimeException("Only sellers can update seller details");
		    }

		    // common fields
		    user.setName(dto.getName());
		    user.setGender(dto.getGender());
		    user.setPhoneNumber(dto.getPhoneNumber());

		    // seller fields
		    user.setShopName(dto.getShopName());
		    user.setGstNumber(dto.getGstNumber());
		    user.setBusinessLicense(dto.getBusinessLicense());
		    user.setShopAddress(dto.getShopAddress());

		    userRepository.save(user);
		    Response response = new Response();
		    response.setStatus(200);
		    response.setMessage("Seller details updated successfully");
		    response.setSellerUpdateDTO(dto);
		    return response;
	}
	
	@Override
	public Long getUserCount() {
	    return userRepository.count();
	}


	

}
