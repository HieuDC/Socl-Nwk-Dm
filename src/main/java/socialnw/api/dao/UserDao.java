package socialnw.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import socialnw.api.entities.User;


/**
 * DAO class for user entity
 */
@Repository
public interface UserDao extends JpaRepository<User,Long> {

}
