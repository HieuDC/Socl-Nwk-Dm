package socialnw.api.dao;

import java.time.Instant;

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
	@Query("UPDATE Account SET otp = ?3, otpExpTime = ?2 WHERE email = ?1")
	void updateOtpByEmail(String email, Instant expTime, String otp);
}
