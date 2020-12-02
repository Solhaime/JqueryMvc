package SpringBootApp.controllers.controllers.exceptionHandler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccessDeniedController {

    @GetMapping("/accessDenied")
    public String forbiddenOrAccessDeniedException(){

        return "/accessDenied";
    }
}
