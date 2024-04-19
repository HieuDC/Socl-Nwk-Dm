package socialnw.api.utils;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class containing validation methods
 */
@Service
public class ValidationUtil {

	private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
	
	private static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$";
	
	@Autowired
	private MessageUtil messageUtil;
	
	/**
	 * Validate user's email
	 * @param email input email
	 * @return true if email is valid
	 */
	public String validateEmail(String email) {
		if (email.length() > 255) {
			return messageUtil.getMessage("MS001","255", null);
		}
		if (!Pattern.compile(EMAIL_PATTERN).matcher(email).matches()) {
			return messageUtil.getMessage("MS002", "");
		}
		return "";
	}
	
	/**
	 * Validate user's password
	 * @param password input password
	 * @return true if password is valid
	 */
	public String validatePassword(String password) {
		if (!Pattern.compile(PASSWORD_PATTERN).matcher(password).matches()) {
			return messageUtil.getMessage("MS003", "");
		}
		return "";
	}
}
