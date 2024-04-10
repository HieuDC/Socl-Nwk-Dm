package socialnw.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import socialnw.api.entities.Account;

@RestController
@RequestMapping("/api")
public class AccountController {
	
	@PostMapping("register")
	public ResponseEntity<Account> register() {
		return null;
	}

}
