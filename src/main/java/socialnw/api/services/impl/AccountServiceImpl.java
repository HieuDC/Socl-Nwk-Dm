package socialnw.api.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import socialnw.api.dao.AccountDao;
import socialnw.api.entities.Account;
import socialnw.api.services.AccountService;

/**
 * Service implementation for managing account
 */
@Service
@Transactional
public class AccountServiceImpl implements AccountService {

	private final AccountDao accountDao;
	
	public AccountServiceImpl(AccountDao accountDao) {
		this.accountDao = accountDao;
	}
	
	@Override
	public Account save(Account account) {
		return accountDao.save(account);
	}

	@Override
	public List<Account> findAll() {
		return accountDao.findAll();
	}

	@Override
	public Optional<Account> findById(Long id) {
		return accountDao.findById(id);
	}

	@Override
	public void deleteById(Long id) {
		accountDao.deleteById(id);;
	}

	@Override
	public Optional<Account> findByEmail(String email) {
		return Optional.ofNullable(accountDao.findByEmail(email));
	}

	@Override
	public void updatePassword(String email, String password) {
		accountDao.updatePassword(email, password);
	}
}
