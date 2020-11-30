package SpringBootApp.web.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SecondAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess( HttpServletRequest httpServletRequest ,
                                         HttpServletResponse httpServletResponse ,
                                         Authentication authentication ) throws IOException, ServletException {
        Long admin = authentication.getAuthorities().stream().filter(grantedAuthority ->
                        grantedAuthority.getAuthority().equals("ADMIN")).count();
        if(admin > 0) {
            httpServletResponse.sendRedirect("/test");
        }else {
            httpServletResponse.sendRedirect("/test");
        }
    }
}
