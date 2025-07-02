package com.hexaware.quitQ_backend_1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.quitQ_backend_1.dto.Response;
import com.hexaware.quitQ_backend_1.request.LoginRequest;
import com.hexaware.quitQ_backend_1.request.RegisterUserRequest;
import com.hexaware.quitQ_backend_1.service.interf.UserService;

@RestController
@RequestMapping("/auth")

public class AuthController {

    private final UserService userService;
    

    public AuthController(UserService userService) {
		super();
		this.userService = userService;
	}
	@PostMapping("/register")
    public ResponseEntity<Response> registerUser(@RequestBody RegisterUserRequest registrationRequest){
        System.out.println(registrationRequest);
        return ResponseEntity.ok(userService.registerUser(registrationRequest));
    }
    @PostMapping("/login")
    public ResponseEntity<Response> loginUser(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(userService.loginUser(loginRequest));
    }
}
