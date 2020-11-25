package DAO.userDao;

import model.Role;
import model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    List<User> listUsers();
    void addUser( User user );
    User  getUserByUsername(String username);
   /* Optional<User> selectUserByUsername( String username );*/
   public void deleteUserById(Long id);

    void updateUserDetails( boolean isActive , Role role , Long id );
    /* public void setUserRoleWhereRoleId(Long id);*/
}
