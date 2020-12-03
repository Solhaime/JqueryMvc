package SpringBootApp.service.userService;

import SpringBootApp.DAO.userDao.UserDao;
import SpringBootApp.model.Role;
import SpringBootApp.model.User;
import SpringBootApp.service.roleService.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@ComponentScan("SpringBootApp.DAO")
public class UserServiceImp implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    @Transactional
    @Override
    public void add( User user ) {
        if(user.getAuthorities().isEmpty()) {
            user.setRole(roleService.getRoleByName("USER"));
        } else {
            Set<Role> roles = user.getAuthorities().stream().map(x ->
                    roleService.getRoleByName(x.getAuthority())).collect(Collectors.toSet());
            user.setRoles(roles);
        }
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
        if(user.getPassword().isEmpty()) {
            user.setPassword(userDao.getUserById(user.getId()).getPassword());
        } else {
            user.setPassword(encodePassword(user.getPassword()));
        }
        if(user.getAuthorities().isEmpty()) {
 /*           Set<Role> collect = userService.getUserById(user.getId()).getAuthorities()
                    .stream().map(x -> roleService.getRoleByName(x.getAuthority())).collect(Collectors.toSet());*/
            user.setRoles(userDao.getUserById(user.getId()).getRoles());
        } else {
            Set<Role> roles = user.getAuthorities().stream().map(x->
                    roleService.getRoleByName(x.getAuthority())).collect(Collectors.toSet());
            user.setRoles(roles);
        }

        userDao.mergeUser(user);
    }


    @Transactional
    @Override
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

}
