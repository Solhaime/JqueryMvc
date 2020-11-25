package service.userService;

import model.Role;
import model.User;

import java.util.List;

public interface UserService {
    void add( User user);
    List<User> listUsers();
    User getUserByUsername(String username);
    public void deleteUserById(Long id);
/*    public void setUserRoleWhereRoleId(Long id);*/
    void updateUserDetails( boolean isActive , Role role , Long id );
}
