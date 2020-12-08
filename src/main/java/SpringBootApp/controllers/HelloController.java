package SpringBootApp.controllers;

import SpringBootApp.model.Role;
import SpringBootApp.model.User;
import SpringBootApp.service.roleService.RoleService;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import SpringBootApp.service.userService.UserService;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HelloController {

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

    Map<String,Object> map;


    @GetMapping("/")
    public String indexPage(){
        return "/index";
    }

    @GetMapping("/loginMain")
    public String loginPage(){
        return "loginMain";
    }

    @GetMapping("/login")
    public String kostil(){
        return "redirect:/test";
    }


    @GetMapping("/register")
    public String registerPage( Model model){
        model.addAttribute("user", new User());
        return "/register";
    }

    @PostMapping("/register")
    public String registerNewUser(@ModelAttribute("user") User user){
        user.addRole("USER");
        userService.add(user);
        return "loginMain";
    }


    @GetMapping("/test")
    public String indexPage( Model model, Principal principal ){
        User user = userService.getUserByUsername(principal.getName());
        map = new HashMap<>();
        map.put("id", Long.valueOf(user.getId()));
        map.put("name", user.getName());
        map.put("lastname",user.getLastname());
        map.put("username", user.getUsername());
        map.put("password", user.getPassword());
        map.put("Authorities", user.getRolesString());
        map.put("user", new User());
        map.put("updatableUser" , new User());
        map.put("accountBlockValue",accountBlockValue);
        map.put("rolesList" , availableRoles);
        map.put("usersList" , userService.listUsers());
        model.addAllAttributes(map);
        return "test";
    }
    @PostMapping("/add")
    public String returnAllUsers( @ModelAttribute("user") User user,
                                  @RequestParam("roleName") String[] roleName, Model model) {
        for(String roles : roleName) {
            Role role = roleService.getRoleByName(roles);
            if(role == null) {
                role = new Role(roles);
            }
            user.setRole(role);
        }
        userService.mergeUser(user);
        map.replace("usersList" , userService.listUsers());
        model.addAllAttributes(map);
        return "redirect:/test";

    }
    @PostMapping("/update")
    public String updateUserDetailsPostController(@ModelAttribute("updatableUser") User user
            , @RequestParam("roleName")String[] roleName, @RequestParam("isActive") String isActive, Model model){
        for(String roles : roleName) {
            Role role = roleService.getRoleByName(roles);
            if(role == null) {
                role = new Role(roles);
            }
            user.setRole(role);
        }
        user.setActive(Boolean.parseBoolean(isActive));
        userService.mergeUser(user);
        map.replace("usersList", userService.listUsers());
        model.addAllAttributes(map);
        return "redirect:/test";
    }

    @PostMapping("/delete")
    public String deleteUserPostController(@RequestParam("id") String id, Model model){
        userService.deleteUserById(Long.parseLong(id));
        map.replace("usersList", userService.listUsers());
        model.addAllAttributes(map);
        return "redirect:/test";
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
