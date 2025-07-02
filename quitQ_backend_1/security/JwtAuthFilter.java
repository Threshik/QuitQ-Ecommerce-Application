package com.hexaware.quitQ_backend_1.security;

import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthFilter extends OncePerRequestFilter{
	
	private final JwtUtils jwtUtils;
    private final  CustomUserDetailsService customUserDetailsService;
    
    @Autowired
	public JwtAuthFilter(JwtUtils jwtUtils, CustomUserDetailsService customUserDetailsService) {
		super();
		this.jwtUtils = jwtUtils;
		this.customUserDetailsService = customUserDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String token = getTokenFromRequest(request);
		
		if(token !=null) {
			
			 String username = jwtUtils.getUsernameFromToken(token);
			 
			 UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
			 
			  if (jwtUtils.isTokenValid(token, userDetails)) {
                  UsernamePasswordAuthenticationToken authenticationToken =
                          new UsernamePasswordAuthenticationToken(
                                  userDetails,
                                  null,
                                  userDetails.getAuthorities()
                          );
                  authenticationToken.setDetails(
                          new WebAuthenticationDetailsSource().buildDetails(request)
                  );

                  SecurityContextHolder.getContext().setAuthentication(authenticationToken);
              }
			
		}
		filterChain.doFilter(request, response);
		
	}
	
	private String getTokenFromRequest(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if(StringUtils.hasText(token) && StringUtils.startsWithIgnoreCase(token, "Bearer")) {
			return token.substring(7);
		}
		return null;
	}

    

}
