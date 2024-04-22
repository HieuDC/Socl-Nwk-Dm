package socialnw.api.services;

/**
 * 
 */
public interface OtpService {

	/**
	 * 
	 * @param email
	 * @return
	 */
	String generateOtp(String email);
	
	/**
	 * 
	 * @param email
	 * @param otp
	 * @return
	 */
	boolean validateOtp(String email, String otp);
}
