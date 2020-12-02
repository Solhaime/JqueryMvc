package SpringBootApp.controllers;


import SpringBootApp.model.Role;
import SpringBootApp.model.User;
import SpringBootApp.service.roleService.RoleService;
import SpringBootApp.service.userService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("api/restful/users")
public class RestController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id){
       userService.deleteUserById(id);
        return true;
    }

    @PostMapping("/update")
    public boolean update(@RequestBody User user){
        if(user.getPassword().equals(null)) {
            user.setPassword(userService.getUserById(user.getId()).getPassword());
        }
        if(user.getAuthorities().equals(null)) {
 /*           Set<Role> collect = userService.getUserById(user.getId()).getAuthorities()
                    .stream().map(x -> roleService.getRoleByName(x.getAuthority())).collect(Collectors.toSet());*/
            user.setRoles(userService.getUserById(user.getId()).getRoles());
        } else {
            Set<Role> roles = user.getAuthorities().stream().map(x->
                    roleService.getRoleByName(x.getAuthority())).collect(Collectors.toSet());
            user.setRoles(roles);
        }
        userService.mergeUser(user);
        return true;
    }

    @PostMapping("/create")
    public boolean add(@RequestBody User user){
        if(user.getAuthorities().equals(null)) {
            user.setRole(roleService.getRoleByName("USER"));
        } else {
            Set<Role> roles = user.getAuthorities().stream().map(x ->
                    roleService.getRoleByName(x.getAuthority())).collect(Collectors.toSet());
            user.setRoles(roles);
        }
        userService.add(user);
        return true;
    }

    @RequestMapping("/all")
    public List<User> getAll(){
       return userService.listUsers();

    }

    @RequestMapping("/{id}")
    public User getById(@PathVariable Long id){
        return userService.getUserById(id);

    }

    @RequestMapping("/byUsername/{username}")
    public User getByUsername(@PathVariable String username){
        return userService.getUserByUsername(username);

    }

}



