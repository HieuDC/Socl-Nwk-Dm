package socialnw.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import socialnw.api.entities.Account;


/**
 * 
 */
@Repository
public interface AccountDao extends JpaRepository<Account,Long> {

	Account findByEmail(String email);
}
