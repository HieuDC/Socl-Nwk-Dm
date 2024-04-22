package socialnw.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import socialnw.api.entities.Account;


/**
 * 
 */
@Repository
public interface AccountDao extends JpaRepository<Account,Long> {

	Account findByEmail(String email);
	
	@Modifying
	@Query("UPDATE accounts SET otp = ?, otp_exp_time = ? WHERE account_id = ?")
	void updateOtpByAccountId(Long accountId, String otp);
}
