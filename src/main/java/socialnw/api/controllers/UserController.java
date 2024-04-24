package socialnw.api.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.Parameter;
import socialnw.api.entities.User;
import socialnw.api.services.UserService;

/**
 * Controller class for user
 */
@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	/**
	 * 
	 * @param user
	 * @return
	 */
	@PostMapping("users")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		User savedUser = userService.save(user);
		return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
	}
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	@GetMapping("users/{userId}")
	public ResponseEntity<User> getUserById(@Parameter(example = "10") Long userId) {
		Optional<User> user = userService.findById(userId);
		if (user.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(user.get(), HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param user
	 * @return
	 */
	@PutMapping("users/{userId}")
	public ResponseEntity<User> updateUser(@RequestBody User user) {
		return new ResponseEntity<>(null, HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param userId
	 */
	@DeleteMapping("users/{userId}")
	public void deleteUser(@Parameter(example = "10") Long userId) {
		
	}
}
