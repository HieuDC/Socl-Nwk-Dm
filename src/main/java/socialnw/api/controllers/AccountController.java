package socialnw.api.controllers;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import socialnw.api.dto.LoginDto;
import socialnw.api.entities.Account;
import socialnw.api.entities.User;
import socialnw.api.services.AccountService;
import socialnw.api.services.JwtService;
import socialnw.api.services.OtpService;
import socialnw.api.services.UserService;
import socialnw.api.utils.MessageUtil;
import socialnw.api.utils.ValidationUtil;

/**
 * Controller class for account
 */
@RestController
@RequestMapping("/api")
public class AccountController {
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private UserService userService; 
	
	@Autowired
	private ValidationUtil validationUtil;
	
	@Autowired
	private MessageUtil messageUtil;
	
	@Autowired
	private OtpService otpService;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private JwtService jwtService;
	
	/**
	 * Register a new account
	 * @param dto registered information
	 * @return result of registration
	 */
	@PostMapping("register")
	public ResponseEntity<Map<String, String>> register(@RequestBody LoginDto dto) {
		String email = dto.getEmail().trim();
		String password = dto.getPassword();
		// Validate email
		String msg = validationUtil.validateEmail(email);
		if (!msg.isEmpty()) {
			return new ResponseEntity<>(Map.of("message", msg), HttpStatus.BAD_REQUEST);
		}
		Optional<Account> acc = accountService.findByEmail(email);
		if (acc.isPresent()) {
			msg = messageUtil.getMessage("MS004", email);
			return new ResponseEntity<>(Map.of("message", msg), HttpStatus.BAD_REQUEST);
		}
		// Validate password
		msg = validationUtil.validatePassword(password);
		if (!msg.isEmpty()) {
			return new ResponseEntity<>(Map.of("message", msg), HttpStatus.BAD_REQUEST);
		}
		// Create account
		User newUser = new User();
		newUser = userService.save(newUser);
		
		Account newAccount = new Account();
		newAccount.setEmail(email);
		newAccount.setPassword(encoder.encode(password));
		newAccount.setUserId(newUser.getUserId());
		newAccount = accountService.save(newAccount);
		msg = messageUtil.getMessage("MS005", email);
		return new ResponseEntity<>(Map.of("message", msg), HttpStatus.CREATED);
	}

	/**
	 * Handle login logic
	 * @param dto login information
	 * @return OTP if login information is valid
	 */
	@PostMapping("login")
	public ResponseEntity<Map<String, String>> login(@RequestBody LoginDto dto) {
		String email = dto.getEmail();
		String password = dto.getPassword();
		Map<String, String> responseData;
		try {
			Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String otp = otpService.generateOtp(email);
			responseData = Map.of("message", messageUtil.getMessage("MS010", ""), "OTP", otp);
			return new ResponseEntity<>(responseData, HttpStatus.OK);
		} catch (DisabledException | LockedException ex) {
			responseData = Map.of("message", messageUtil.getMessage("MS007", email));
			return new ResponseEntity<>(responseData, HttpStatus.FORBIDDEN);
		} catch (BadCredentialsException ex) {
			responseData = Map.of("message", messageUtil.getMessage("MS006", ""));
			return new ResponseEntity<>(responseData, HttpStatus.FORBIDDEN);
		}
	}
	
	/**
	 * Handle authentication using OTP
	 * @param params authentication information
	 * @return token if authentication is successfully
	 */
	@PostMapping("authenticate")
	public ResponseEntity<Map<String, String>> authenticate(@RequestBody Map<String, String> params) {
		String email = params.get("email").trim();
		String otp = params.get("otp");
		Map<String, String> responseData;
		if (!otpService.validateOtp(email, otp)) {
			responseData = Map.of("message", messageUtil.getMessage("MS008", ""));
			return new ResponseEntity<>(responseData, HttpStatus.UNAUTHORIZED); // Need WWW-Authenticate header
		}
		// Create token
		String token = jwtService.generateAccessToken(email);
		responseData = Map.of("message", messageUtil.getMessage("MS009", email), "token", token);
		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}
	
	/**
	 * Start password reset process
	 * @param params input
	 * @return token if the request is valid
	 */
	@PostMapping("reset-password/init")
	public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> params) {
		String email = params.get("email").trim();
		Map<String, String> responseData;
		Optional<Account> account = accountService.findByEmail(email);
		if (account.isEmpty()) {
			responseData = Map.of("message", messageUtil.getMessage("MS011", ""));
			return new ResponseEntity<>(responseData, HttpStatus.OK);
		}
		// Create token
		String token = jwtService.generatePasswordResetToken(account.get());
		responseData = Map.of("message", messageUtil.getMessage("MS012", ""), "token", token);
		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}
	
	/**
	 * Change password
	 * @param authHeader authorization header
	 * @param params input information
	 * @return result
	 */
	@PostMapping("reset-password/finish")
	public ResponseEntity<Map<String, String>> resetPassword(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
			@RequestBody Map<String, String> params) {
		String email = params.get("email").trim();
		String newPassword = params.get("password");
		Map<String, String> responseData;
		Optional<Account> account = accountService.findByEmail(email);
		if (account.isEmpty()) {
			responseData = Map.of("message", messageUtil.getMessage("MS011", ""));
			return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
		}
		if (authHeader == null || !authHeader.startsWith("Bearer")) {
			responseData = Map.of("message", messageUtil.getMessage("MS014", ""));
			return new ResponseEntity<>(responseData, HttpStatus.UNAUTHORIZED);
		}
		String token = authHeader.substring(7);
		if (!jwtService.validatePasswordResetToken(token, account.get())) {
			responseData = Map.of("message", messageUtil.getMessage("MS014", ""));
			return new ResponseEntity<>(responseData, HttpStatus.UNAUTHORIZED);
		}
		// Check password policy
		String msg = validationUtil.validatePassword(newPassword);
		if (!msg.isEmpty()) {
			return new ResponseEntity<>(Map.of("message", msg), HttpStatus.BAD_REQUEST);
		}
		accountService.updatePassword(email, encoder.encode(newPassword));
		responseData = Map.of("message", messageUtil.getMessage("MS013", ""));
		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}
}
