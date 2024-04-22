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
	 * @return
	 */
	boolean validateOtp();
}
