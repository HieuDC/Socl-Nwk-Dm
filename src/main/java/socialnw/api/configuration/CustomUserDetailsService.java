package socialnw.api.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import socialnw.api.dao.AccountDao;
import socialnw.api.entities.Account;

public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private AccountDao accountDao; 
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Account account = accountDao.findByEmail(email);
		if (account == null) {
			throw new UsernameNotFoundException(email);
		}
		return new UserPrincipal(account);
	}

}
