package socialnw.api.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import socialnw.api.dao.UserDao;
import socialnw.api.entities.User;
import socialnw.api.services.UserService;

/**
 * Service implementation for managing user
 */
@Service
public class UserServiceImpl implements UserService {

	private final UserDao userDao;
	
	public UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}
	
	@Override
	public User save(User user) {
		return userDao.save(user);
	}

	@Override
	public List<User> findAll() {
		return userDao.findAll();
	}

	@Override
	public Optional<User> findById(Long id) {
		return userDao.findById(id);
	}

	@Override
	public void deleteById(Long id) {
		userDao.deleteById(id);;
	}

}
