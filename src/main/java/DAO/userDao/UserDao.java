package DAO.userDao;

import model.Role;
import model.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserDao {
    List<User> listUsers();

    void addUser( User user );

    User  getUserByUsername(String username);

    public void deleteUserById(Long id);

    void updateUserDetails( boolean isActive, Long id );

    User  getUserById( Long id );

    void mergeUser( User user );

}
