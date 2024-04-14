package socialnw.api.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import socialnw.api.entities.Account;

@RestController
@RequestMapping("/api")
public class AccountController {
	
	@Autowired
	private AuthenticationManager authManager;
	
	@PostMapping("register")
	public ResponseEntity<Account> register() {
		return null;
	}

	@PostMapping("login")
	public ResponseEntity<Account> login(@RequestBody Map<String, String> params) {
		String email = params.get("email");
		String password = params.get("password");
		Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return ResponseEntity.ok(null);
	}
	
	@PostMapping("authenticate")
	public ResponseEntity<Account> authenticate(@RequestBody Map<String, String> params) {
		String otp = params.get("otp");
		String email = params.get("email");
		return null;
	}
}
