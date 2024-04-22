package socialnw.api.controllers;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import socialnw.api.dto.LoginDto;
import socialnw.api.entities.Account;
import socialnw.api.entities.User;
import socialnw.api.services.AccountService;
import socialnw.api.services.OtpService;
import socialnw.api.services.UserService;
import socialnw.api.utils.MessageUtil;
import socialnw.api.utils.ValidationUtil;

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
	
	/**
	 * 
	 * @param dto
	 * @return
	 */
	@PostMapping("register")
	public ResponseEntity<String> register(@RequestBody LoginDto dto) {
		String email = dto.getEmail().trim();
		String password = dto.getPassword();
		// Validate email
		String msg = validationUtil.validateEmail(email);
		if (!msg.isEmpty()) {
			return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
		}
		Optional<Account> acc = accountService.findByEmail(email);
		if (acc.isPresent()) {
			msg = messageUtil.getMessage("MS004", "");
			return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
		}
		// Validate password
		msg = validationUtil.validatePassword(password);
		if (!msg.isEmpty()) {
			return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
		}
		// Create account
		User newUser = new User();
		newUser.setEmail(email);
		newUser = userService.save(newUser);
		
		Account newAccount = new Account();
		newAccount.setEmail(email);
		newAccount.setPassword(encoder.encode(password));
		newAccount.setUserId(newUser.getUserId());
		newAccount = accountService.save(newAccount);
		msg = messageUtil.getMessage("MS005", "");
		return new ResponseEntity<>(msg, HttpStatus.CREATED);
	}

	@PostMapping("login")
	public ResponseEntity<String> login(@RequestBody LoginDto dto) {
		String email = dto.getEmail();
		String password = dto.getPassword();
		try {
			Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String otp = otpService.generateOtp(email);
			return new ResponseEntity<>(otp, HttpStatus.OK);
		} catch (DisabledException | LockedException ex) {
			return new ResponseEntity<>(messageUtil.getMessage("MS007", ""), HttpStatus.FORBIDDEN);
		} catch (BadCredentialsException ex) {
			return new ResponseEntity<>(messageUtil.getMessage("MS006", ""), HttpStatus.FORBIDDEN);
		}
	}
	
	@PostMapping("authenticate")
	public ResponseEntity<String> authenticate(@RequestBody Map<String, String> params) {
		String email = params.get("email");
		String otp = params.get("otp");
		if (!otpService.validateOtp(email, otp)) {
			return new ResponseEntity<>("Invalid OTP", HttpStatus.UNAUTHORIZED); // Need WWW-Authenticate header
		}
		// Create token
		return new ResponseEntity<>("Login successfully", HttpStatus.OK);
	} 
}
