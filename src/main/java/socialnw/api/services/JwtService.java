package socialnw.api.services;

import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;
import socialnw.api.entities.Account;

/**
 * Service class for JWT related functions
 */
public interface JwtService {
	
	/**
	 * Generate accessing JWT
	 * @param email user's email
	 * @return generated token
	 */
	String generateAccessToken(String email);
	
	/**
	 * Extract information from token
	 * @param token input token
	 * @return claims containing information
	 */
	Claims extractAllClaims(String token);
	
	/**
	 * Check if token is expired
	 * @param token input token
	 * @return true if token is expired
	 */
	boolean isTokenExpired(String token);
	
	/**
	 * Validate token
	 * @param token input token
	 * @return true if token is valid
	 */
	boolean validateToken(String token, UserDetails userDetail);
	
	/**
	 * 
	 * @param account
	 * @return
	 */
	String generatePasswordResetToken(Account account);
	
	/**
	 * 
	 * @param token
	 * @param email
	 * @return
	 */
	boolean validatePasswordResetToken(String token, Account account);
}
