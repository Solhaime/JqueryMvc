package web.controllers.basePagesPermitedForAll;

import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import service.userService.UserService;

@Controller
public class PermitedForAllController {

    @Autowired
    UserService userService;

    @GetMapping("/")
    public String indexPage(){
        return "/index";
    }

/*
    @PostMapping("login")
    public String loginPage() {
        return "/user";
    }

    @GetMapping("login")
    public String loginPag1e() {
        return "/login";
    }
*/

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
}
