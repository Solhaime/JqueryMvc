package web.controllers.roleAdminController;

import model.Role;
import model.User;
import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.userService.UserService;
import service.roleService.RoleService;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    @Qualifier("accountBlockingValues")
    List<String> accountBlockValue;

    @Autowired
    @Qualifier("availableRoles")
    List<String> availableRoles;



    @GetMapping("/users/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String newUser( Model model ) {
        model.addAttribute("user" , new User());
        model.addAttribute("roles" , availableRoles);
        return "add";
    }

    @PostMapping("users/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String returnAllUsers( @ModelAttribute("user") User user,
                                  @RequestParam("roleName") String roleName,
                                  @RequestParam("username") String username, Model model ) {
        model.addAttribute("takenUsername", username);
        Role role = roleService.getRoleByName(roleName);
        if(role == null) {
            role = new Role(roleName);
        }
        user.setRole(role);
            userService.mergeUser(user);
            return "/success";

    }

    @GetMapping("/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String retrieveAllUsersFromDataBase( Model model ) {
        model.addAttribute("usersList" , userService.listUsers());
        return "retrieveall";
    }

    @GetMapping("users/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteUserGetController(){
        return "/delete";
    }

    @PostMapping("users/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteUserPostController(@RequestParam("id") String id){
        userService.deleteUserById(Long.parseLong(id));
        return "successdel";
    }

    @GetMapping("users/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String updateUserDetailsGetController(Model model){
        model.addAttribute("updatableUser" , new User());
        model.addAttribute("accountBlockValue",accountBlockValue);
        model.addAttribute("rolesList" , availableRoles);
        return "update";
    }

    @PostMapping("users/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String updateUserDetailsPostController(@ModelAttribute("updatableUser") User user
            , @RequestParam("roleName")String roleName,@RequestParam("isActive") String isActive){
        Role role = roleService.getRoleByName(roleName);
        if(role == null) {
            role = new Role(roleName);
        }
        user.setRole(role);
        user.setActive(Boolean.parseBoolean(isActive));
        userService.mergeUser(user);
        return "successupd";
    }
    @ExceptionHandler(HibernateException.class)
    public String constraintExceptionHandler(){
        return "constraintusername";
    }

    @ExceptionHandler(NumberFormatException.class)
    public String idFormatExceptionHandler(){
        return "numberformatexc";
    }
}
