package SpringBootApp.DAO.userDao;

import SpringBootApp.model.User;

import java.util.List;

public interface UserDao {
    List<User> listUsers();

    void addUser( User user );

    User  getUserByUsername(String username);

    public void deleteUserById(Long id);

    void updateUserDetails( boolean isActive, Long id );

    User  getUserById( Long id );

    void mergeUser( User user );

}
