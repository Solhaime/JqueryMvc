package service.userService;

import DAO.userDao.UserDao;
import model.Role;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@ComponentScan("DAO")
public class UserServiceImp implements UserService {

    @Autowired
    private UserDao userDao;



    @Transactional
    @Override
    public void add( User user ) {
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

    @Transactional
    @Override
    public void updateUserDetails( boolean isActive , Role role , Long id ) {
        userDao.updateUserDetails(isActive,role,id);
    }

/*    @Override
    public void setUserRoleWhereRoleId( Long id ) {
        userDao.setUserRoleWhereRoleId(id);
    }*/
}
