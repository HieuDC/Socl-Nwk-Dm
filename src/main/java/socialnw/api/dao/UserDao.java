package socialnw.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import socialnw.api.entities.User;


/**
 * 
 */
@Repository
public interface UserDao extends JpaRepository<User,Long> {

}
