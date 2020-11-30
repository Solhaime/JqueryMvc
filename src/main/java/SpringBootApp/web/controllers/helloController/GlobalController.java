/*
package SpringBootApp.web.controllers.basePagesPermitedForAll;

import SpringBootApp.model.Role;
import SpringBootApp.model.User;
import SpringBootApp.service.roleService.RoleService;
import SpringBootApp.service.userService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class GlobalController {

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
    public String indexPage( Model model){
        model.addAttribute("user", new User());
        model.addAttribute("updatableUser" , new User());
        model.addAttribute("accountBlockValue",accountBlockValue);
        model.addAttribute("rolesList" , availableRoles);
        model.addAttribute("usersList" , userService.listUsers());
        return "/test";
    }

    @PostMapping("/register")
    public String registerNewUser(@ModelAttribute("user") User user){
        user.addRole("USER");
        userService.add(user);
        return "/login";
    }

    @PostMapping("users/add")
    @PreAuthorize("hasAuthority('ADMIN')")
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
        return "/success";

    }

    @PostMapping("users/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String updateUserDetailsPostController(@ModelAttribute("updatableUser") User user
            , @RequestParam("roleName")String[] roleName, @RequestParam("isActive") String isActive){
        for(String roles : roleName) {
            Role role = roleService.getRoleByName(roles);
            if(role == null) {
                role = new Role(roles);
            }
            user.setRole(role);
        }
        user.setActive(Boolean.parseBoolean(isActive));
        userService.mergeUser(user);
        return "successupd";
    }

    @PostMapping("users/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteUserPostController(@RequestParam("id") String id){
        userService.deleteUserById(Long.parseLong(id));
        return "successdel";
    }
}
*/
