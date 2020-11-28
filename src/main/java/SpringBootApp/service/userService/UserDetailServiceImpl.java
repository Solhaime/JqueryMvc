package SpringBootApp.service.userService;

import SpringBootApp.DAO.userDao.UserDaoImp;
import SpringBootApp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("detailService")
@ComponentScan("SpringBootApp.DAO")
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserDaoImp dao;

    @Override
    public UserDetails loadUserByUsername( String username ) throws UsernameNotFoundException {
    User user = null;
        try {
        user = dao.getUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
            throw new UsernameNotFoundException(e.getMessage());
        }
        return user;
    }

}
