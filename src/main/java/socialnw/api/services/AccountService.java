package socialnw.api.services;

import java.util.List;
import java.util.Optional;

import socialnw.api.entities.Account;

/**
 * Interface for managing account
 */	
public interface AccountService {
	
	/**
	 * Save an account
	 * @param account the entity to save
	 * @return the persisted entity
	 */
	Account save(Account account);
	
	/**
	 * Get all the account
	 * @return the list of entity
	 */
	List<Account> findAll();
	
	/**
	 * Get the account with specified ID
	 * @param id user's ID
	 * @return the entity
	 */
	Optional<Account> findById(Long id);
	
	/**
	 * Delete the account with specified ID
	 * @param id user's ID
	 */
	void deleteById(Long id);
	
	/**
	 * Get the account with specified email
	 * @param id user's email
	 * @return the entity
	 */
	Optional<Account> findByEmail(String email);
	
	/**
	 * Update account's password
	 * @param email user's email
	 * @param password new password
	 */
	void updatePassword(String email, String password);
}
