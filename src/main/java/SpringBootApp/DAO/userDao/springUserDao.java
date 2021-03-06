package SpringBootApp.DAO.userDao;

import SpringBootApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface springUserDao extends JpaRepository<User, Long> {

    @Query("select user from User user where user.username =:username")
    User getByUsername(@Param("username") String username);
/*    @Query("delete distinct user from User user where user.username =:username")
    void deleteById(Long id);*/
}
