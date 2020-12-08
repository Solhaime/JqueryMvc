package SpringBootApp.DAO.userDao;

import SpringBootApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.oauth2.provider.ClientRegistrationService;

import java.util.Optional;

public interface springUserDao extends JpaRepository<User, Long>{

    @Query("select user from User user JOIN FETCH user.roles where user.username =:username")
    Optional<User> getByUsername( @Param("username") String username);
/*    @Query("delete distinct user from User user where user.username =:username")
    void deleteById(Long id);*/
}
