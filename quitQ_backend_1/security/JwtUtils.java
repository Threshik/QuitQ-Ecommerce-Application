package com.hexaware.quitQ_backend_1.security;

import java.nio.charset.StandardCharsets;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.hexaware.quitQ_backend_1.entities.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;

//generates the JWT
@Service
public class JwtUtils {
		private static final long EXPIRATION_TIME_IN_MILLSEC=1000L * 60L * 60L * 24L * 30L; //30days
		private SecretKey secretKey; // it stores the key used to sign the JWTs
		
		@Value("${secreteJwtString}")
		private String secreteJwtString; // 32 characters in application properties
		
		//logging without lombok
		private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
		
		//converts the string secret key into bytes
		// runs after the bean creation
		@PostConstruct
		private void init() {
			byte[] keyBytes = secreteJwtString.getBytes(StandardCharsets.UTF_8);
			this.secretKey = new SecretKeySpec(keyBytes, "HmacSHA256");
			logger.info("JWT secret key initialized successfully");
		}
		
		public String generateToken(User user) {
			String username = user.getEmail();
			return generateToken(username);
			
		}
	
		public String generateToken(String username) {
			return Jwts.builder()
		            .subject(username)
		            .issuedAt(new Date(System.currentTimeMillis()))
		            .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_IN_MILLSEC))
		            .signWith(secretKey)
		            .compact();
		}
		
		//extract the username from the token
		public String getUsernameFromToken(String token) {
			return extractClaims(token, claims -> claims.getSubject());
		}
	
		private <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
			 return claimsResolver.apply(
				        Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload()
				    );
		}
		
		public boolean isTokenValid(String token, UserDetails userDetails) {
		    final String username = getUsernameFromToken(token);
		    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
		}
		
		private boolean isTokenExpired(String token) {
		    return extractClaims(token, Claims::getExpiration).before(new Date());
		}



	
	
	
	
}
