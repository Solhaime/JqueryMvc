package SpringBootApp.config;


import SpringBootApp.DAO.roleDao.springRoleDao;
import SpringBootApp.DAO.userDao.springUserDao;
import SpringBootApp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import SpringBootApp.service.userService.UserDetailServiceImpl;
import org.springframework.transaction.annotation.Transactional;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableOAuth2Sso
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailServiceImpl userService;

    @Autowired
    private springRoleDao roleDao;
    @Autowired
    public SecurityConfig( UserDetailServiceImpl userService ) {
        this.userService = userService;
    }



    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(provider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .formLogin().loginPage("/login").loginProcessingUrl("/login")
              .defaultSuccessUrl("/test")/*.and().exceptionHandling().accessDeniedPage("/accessDenied")*/
                    .and().logout();



    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public DaoAuthenticationProvider provider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userService);
        return provider;
    }
    @Bean
    public PrincipalExtractor principalExtractor( springUserDao userDao){
        return map -> {
            String username =(String) map.get("email");
            User user = userDao.getByUsername(username).orElseGet(() -> {
                User newUser = new User();
                newUser.setUsername(username);
                newUser.setPassword(passwordEncoder().encode("password"));
                newUser.setName((String) map.get("name"));
                newUser.setRole(roleDao.getRoleByName("USER"));
                return newUser;
            });
            return userDao.save(user);
        };
    }
}
