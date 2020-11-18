package web.controller;

import config.AppConfig;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import service.UserService;

@Controller
public class UserController {

    @GetMapping("/")
    public String newUser(Model model){
        model.addAttribute("user", new User());
        return "/users";
    }
    @PostMapping("/users")
    public String returnAllUsers(@ModelAttribute("user") User user){
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);
        UserService bean = context.getBean(UserService.class);
        bean.add(user);
        return "/success";
    }
    @GetMapping("/result")
    public String getAllUsers(Model model){
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);
        UserService bean = context.getBean(UserService.class);
        model.addAttribute("usersList", bean.listUsers());
        return "/result";
    }







/*@PostMapping
    public String createUser( @RequestParam("name") String name,@RequestParam("lastname") String lastname, Model model){
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);
        UserService bean = context.getBean(UserService.class);
        bean.add(new User(name, lastname));
        model.addAttribute()
    }*/
}
