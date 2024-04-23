package socialnw.api.services;

import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;

public interface JwtService {
	
	/**
	 * 
	 * @param email
	 * @return
	 */
	String generateToken(String email);
	
	/**
	 * 
	 * @param token
	 * @return
	 */
	Claims extractAllClaims(String token);
	
	/**
	 * 
	 * @param token
	 * @return
	 */
	boolean isTokenExpired(String token);
	
	/**
	 * 
	 * @param token
	 * @return
	 */
	boolean validateToken(String token, UserDetails userDetail);
}
