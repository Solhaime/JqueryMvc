package SpringBootApp.web.controllers.basePagesPermitedForAll;

import SpringBootApp.model.User;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import SpringBootApp.service.userService.UserService;

@Controller
public class HelloController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String indexPage(){
        return "/test";
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

    @GetMapping("/test")
    public String globalPage(){
        return "/test";
    }

    @ExceptionHandler(HibernateException.class)
    public String constraintExceptionHandler(){
        return "constraintusername";
    }
}
