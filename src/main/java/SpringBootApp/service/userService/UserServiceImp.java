package SpringBootApp.service.userService;

import SpringBootApp.DAO.userDao.UserDao;
import SpringBootApp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@ComponentScan("SpringBootApp.DAO")
public class UserServiceImp implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void add( User user ) {
        user.setPassword(encodePassword(user.getPassword()));
        userDao.addUser(user);
    }

    @Transactional
    @Override
    public List<User> listUsers() {
        return userDao.listUsers();
    }

    @Override
    public User getUserByUsername( String username ) {
        return userDao.getUserByUsername(username);
    }

    @Transactional
    @Override
    public void deleteUserById( Long id ) {
        userDao.deleteUserById(id);
    }


    @Override
    public User getUserById( Long id ) {
       return userDao.getUserById(id);
    }

    @Transactional
    @Override
    public void mergeUser( User user ) {
        user.setPassword(encodePassword(user.getPassword()));
        userDao.mergeUser(user);
    }

    @Transactional
    @Override
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

}
