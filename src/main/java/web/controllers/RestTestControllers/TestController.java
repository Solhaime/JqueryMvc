package web.controllers.RestTestControllers;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
public class TestController {
    @GetMapping("/test")
    public String test( Authentication authentication ){
       return String.valueOf(authentication.getAuthorities().stream().filter(grantedAuthority -> grantedAuthority.getAuthority().equals("ADMIN")).count());
    }

/*    @PostMapping("users/delete")
    public String testTest(@RequestParam("id") String id ){
        return id;
    }*/
}
