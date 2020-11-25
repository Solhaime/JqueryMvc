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
import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminAccessController {
    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier("availableRoles")
    List<Role> roles;

    @Autowired
    @Qualifier("accountBlockingValues")
    List<String> accountBlockValue;


    @GetMapping("/users/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String newUser( Model model ) {
        model.addAttribute("user" , new User());
        model.addAttribute("roles" , roles);
        return "add";
    }

    @PostMapping("users/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String returnAllUsers( @ModelAttribute("user") User user,
                                  @RequestParam("roleName") String role,
                                  @RequestParam("username") String username, Model model ) {
        model.addAttribute("takenUsername", username);
        try {
            user.addRole(role);
            userService.add(user);
            return "/success";
        }catch(ConstraintViolationException e){
            System.out.println("Could not execute statement because user with chosen name already exists");
            return "add";
        }
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
        model.addAttribute("accountBlockValue",accountBlockValue);
        model.addAttribute("rolesList" , roles);
        return "update";
    }

    @PostMapping("users/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String updateUserDetailsPostController(@RequestParam("id")String id
            , @RequestParam("role")String role,@RequestParam("isActive") String isActive){
        User user = userService.getUserById(Long.parseLong(id));
        user.changeRole(role);
        userService.mergeUser(user);
        userService.updateUserDetails(Boolean.parseBoolean(isActive),Long.parseLong(id));
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
