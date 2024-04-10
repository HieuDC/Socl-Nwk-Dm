package socialnw.api.services;

import java.util.List;
import java.util.Optional;

import socialnw.api.entities.User;

/**
 * Interface for managing user
 */
public interface UserService {
	
	/**
	 * Save a user
	 * @param user the entity to save
	 * @return the persisted entity
	 */
	User save(User user);
	
	/**
	 * Get all the user
	 * @return the list of entity
	 */
	List<User> findAll();
	
	/**
	 * Get the user with specified ID
	 * @param id user's ID
	 * @return the entity
	 */
	Optional<User> findById(Long id);
	
	/**
	 * Delete the user with specified ID
	 * @param id user's ID
	 */
	void deleteById(Long id);
}
