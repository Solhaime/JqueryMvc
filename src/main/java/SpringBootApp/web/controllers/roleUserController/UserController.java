package SpringBootApp.web.controllers.roleUserController;


import SpringBootApp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import SpringBootApp.service.userService.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/users")
public class UserController {


    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String currentUserName(Principal principal, Model model) {
        User user = userService.getUserByUsername(principal.getName());
        /*model.addAttribute("userDetail", user.toString())*/
        model.addAttribute("id", user.getId());
        model.addAttribute("name", user.getName());
        model.addAttribute("lastname",user.getLastname());
        model.addAttribute("username", user.getUsername());
        model.addAttribute("password", user.getPassword());
        model.addAttribute("Authorities", user.getRolesString());
        return "/test";
    }






/*@PostMapping
    public String createUser( @RequestParam("name") String name,@RequestParam("lastname") String lastname, Model model){
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(SpringBootApp.config.AppConfig.class);
        UserService bean = context.getBean(UserService.class);
        bean.add(new User(name, lastname));
        model.addAttribute()
    }*/
}

