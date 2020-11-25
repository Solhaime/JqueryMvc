package web.controllers.exceptionHandler;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ForbiddenOrAccessDeniedExceptionController {

    @GetMapping("/accessDenied")
    public String forbiddenOrAccessDeniedException(){

        return "/accessDenied";
    }
}
