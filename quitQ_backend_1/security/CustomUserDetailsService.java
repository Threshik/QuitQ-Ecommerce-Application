package com.hexaware.quitQ_backend_1.security;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import com.hexaware.quitQ_backend_1.entities.User;
import com.hexaware.quitQ_backend_1.exception.ResourceNotFoundException;
import com.hexaware.quitQ_backend_1.repository.UserRepository;

// finds the user from db
@Service
public class CustomUserDetailsService implements UserDetailsService{

	private final UserRepository userRepository;

	public CustomUserDetailsService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		 User user = userRepository.findByEmail(username)
	                .orElseThrow(()-> new ResourceNotFoundException("User/ Email Not found"));
		return new AuthUser(user);
	}

	

}
