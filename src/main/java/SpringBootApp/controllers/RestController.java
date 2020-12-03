package SpringBootApp.controllers;


import SpringBootApp.model.Role;
import SpringBootApp.model.User;
import SpringBootApp.service.roleService.RoleService;
import SpringBootApp.service.userService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Void> delete(@PathVariable Long id){
       userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update")
    public ResponseEntity<Void> update(@RequestBody User user){
        userService.mergeUser(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/create")
    public ResponseEntity<Void> add(@RequestBody User user){
        userService.add(user);
        return ResponseEntity.ok().build();
    }

    @RequestMapping("/all")
    public ResponseEntity<List> getAll(){
       return ResponseEntity.ok(userService.listUsers());

    }

    @RequestMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUserById(id));

    }

    @RequestMapping("/byUsername/{username}")
    public ResponseEntity<User> getByUsername( @PathVariable String username){
        return ResponseEntity.ok(userService.getUserByUsername(username));

    }

}



