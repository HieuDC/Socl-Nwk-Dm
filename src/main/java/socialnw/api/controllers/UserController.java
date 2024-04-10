package socialnw.api.controllers;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.Parameter;
import socialnw.api.entities.User;
import socialnw.api.services.UserService;

/**
 * 
 */
@RestController
@RequestMapping("/api")
public class UserController {
	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("users")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		User savedUser = userService.save(user);
		return ResponseEntity.ok(savedUser);
	}
	
	@GetMapping("users/{userId}")
	public ResponseEntity<User> getUserById(@Parameter(example = "10") Long userId) {
		Optional<User> user = userService.findById(userId);
		if (user.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(user.get());
	}
}
