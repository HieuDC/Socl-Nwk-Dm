package socialnw.api.services;

/**
 * Service class for handling OTP-related logic
 */
public interface OtpService {

	/**
	 * Generate OTP
	 * @param email user's email
	 * @return OTP for logging in
	 */
	String generateOtp(String email);
	
	/**
	 * Validate OTP
	 * @param email user's email
	 * @param otp OTP from request
	 * @return true if OTP is matched and not expired
	 */
	boolean validateOtp(String email, String otp);
}
