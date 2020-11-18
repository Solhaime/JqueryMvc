package service;

import DAO.UserDao;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
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
}
