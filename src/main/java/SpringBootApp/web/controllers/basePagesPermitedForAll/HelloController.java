package SpringBootApp.web.controllers.basePagesPermitedForAll;

import SpringBootApp.model.Role;
import SpringBootApp.model.User;
import SpringBootApp.service.roleService.RoleService;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import SpringBootApp.service.userService.UserService;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

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


    @GetMapping("/")
    public String indexPage(){
        return "/login";
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
        return "/login";
    }

/*    @GetMapping("/test")
    public String globalPage(){
        return "/test";
    }*/

    @ExceptionHandler(HibernateException.class)
    public String constraintExceptionHandler(){
        return "constraintusername";
    }

    @GetMapping("/test")
    public String indexPage( Model model, Principal principal ){
        User user = userService.getUserByUsername(principal.getName());
        /*model.addAttribute("userDetail", user.toString())*/
        model.addAttribute("id", user.getId());
        model.addAttribute("name", user.getName());
        model.addAttribute("lastname",user.getLastname());
        model.addAttribute("username", user.getUsername());
        model.addAttribute("password", user.getPassword());
        model.addAttribute("Authorities", user.getRolesString());
        model.addAttribute("user", new User());
        model.addAttribute("updatableUser" , new User());
        model.addAttribute("accountBlockValue",accountBlockValue);
        model.addAttribute("rolesList" , availableRoles);
        model.addAttribute("usersList" , userService.listUsers());
        return "/test";
    }
    @PostMapping("/add")
    public String returnAllUsers( @ModelAttribute("user") User user,
                                  @RequestParam("roleName") String[] roleName) {
        for(String roles : roleName) {
            Role role = roleService.getRoleByName(roles);
            if(role == null) {
                role = new Role(roles);
            }
            user.setRole(role);
        }
        userService.mergeUser(user);
        return "/test";

    }
}
