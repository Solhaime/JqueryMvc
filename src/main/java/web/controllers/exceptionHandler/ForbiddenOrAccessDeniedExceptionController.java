package web.controllers.exceptionHandler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ForbiddenOrAccessDeniedExceptionController {

    @GetMapping("/accessDenied")
    public String forbiddenOrAccessDeniedException(){

        return "/accessDenied";
    }
}
