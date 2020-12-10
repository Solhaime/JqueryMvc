package SpringBootApp.controllers;


import SpringBootApp.model.Role;
import SpringBootApp.model.User;
import SpringBootApp.service.roleService.RoleService;
import SpringBootApp.service.userService.UserService;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

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
    public ResponseEntity<Void> deleteById( @PathVariable Long id ) {
        // userService.deleteUserById(id);
        userService.springDataDeleteById(id);
        return ResponseEntity.ok().build();
    }
//    @DeleteMapping("/delete/username/{username}")
//    public ResponseEntity<Void> deleteByUsername( @PathVariable String username ) {
//        // userService.deleteUserById(id);
//        userService.deleteUserByUsername(username);
//        return ResponseEntity.ok().build();
//    }

    @PostMapping("/update")
    public ResponseEntity<Void> update( @RequestBody User user ) {
        //    userService.mergeUser(user);
        userService.springDataMerge(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/create")
    public ResponseEntity<Void> add( @RequestBody User user ) {
        //   userService.add(user);
        userService.springDataSave(user);
        return ResponseEntity.ok().build();
    }

    @RequestMapping("/all")
    public ResponseEntity<List> getAll() {
        // return ResponseEntity.ok(userService.listUsers());
        return ResponseEntity.ok(userService.springDataFindAll());
    }

    @RequestMapping("/{id}")
    public ResponseEntity<User> getById( @PathVariable Long id ) {
        // return ResponseEntity.ok(userService.getUserById(id));
        return ResponseEntity.ok(userService.springDataGetById(id));
    }

    @RequestMapping("/byUsername/{username}")
    public ResponseEntity<User> getByUsername( @PathVariable String username ) {
        //  return ResponseEntity.ok(userService.getUserByUsername(username));
        return ResponseEntity.ok(userService.springDataGetByName(username).get());
    }

    @PostMapping("/password")
    public ResponseEntity<Void> changePassword( @RequestBody User user ) {
        userService.springDataChangeUserPassword(user);
        return ResponseEntity.ok().build();
    }
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Constraint username error occurred") //500
    @ExceptionHandler(HibernateException.class)
    public void constraintExceptionHandler(){
      //  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Constraint username error occurred");
    }

    @ExceptionHandler(NumberFormatException.class)
    public void idFormatExceptionHandler(){
    }

}



