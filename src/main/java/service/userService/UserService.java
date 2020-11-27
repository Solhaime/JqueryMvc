package service.userService;

import model.Role;
import model.User;

import java.util.Collection;
import java.util.List;

public interface UserService {
    void add( User user);

    List<User> listUsers();

    User getUserByUsername(String username);

    public void deleteUserById(Long id);

    public User  getUserById( Long id );

    public void mergeUser(User user);

    String encodePassword(String password);
}
