package SpringBootApp.service.userService;

import SpringBootApp.model.Role;
import SpringBootApp.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface UserService {
    void add( User user);

    List<User> listUsers();

    User getUserByUsername(String username);

    public void deleteUserById(Long id);

    public User  getUserById( Long id );

    public void mergeUser(User user);

    String encodePassword(String password);

    public List<User> springDataFindAll();

    public void springDataSave( User user);

    public User springDataGetByName( String username);

    public User springDataGetById( Long id);

    public void springDataDeleteById( Long id);

    public void springDataMerge( User user);
}
