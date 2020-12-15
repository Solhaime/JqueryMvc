package SpringBootApp.config;


import SpringBootApp.DAO.roleDao.springRoleDao;
import SpringBootApp.DAO.userDao.springUserDao;
import SpringBootApp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import SpringBootApp.service.userService.UserDetailServiceImpl;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;

import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.Filter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
//@EnableOAuth2Client
@EnableOAuth2Sso
//@EnableAuthorizationServer
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailServiceImpl userService;

    @Autowired
    private springRoleDao roleDao;
/*
    @Autowired
    private springUserDao userDao;
    @Qualifier("oauth2ClientContext")
    @Autowired
    private OAuth2ClientContext oAuth2ClientContext;
*/

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
                .formLogin().loginPage("/login").loginProcessingUrl("/login").loginPage("/loginMain")
                .loginProcessingUrl("/loginMain").defaultSuccessUrl("/test")
                .and().logout().invalidateHttpSession(true).clearAuthentication(true).deleteCookies("JSESSIONID").logoutSuccessUrl("/");//.and().addFilterBefore(ssoFilter(), UsernamePasswordAuthenticationFilter.class).oauth2Login().loginPage("/login").defaultSuccessUrl("/test");
/*        http.authorizeRequests().antMatchers("/login").permitAll()
                .anyRequest().authenticated()
                .and().formLogin() // enable form based login
                .loginPage("/login").defaultSuccessUrl("/test")
                .and().logout() // enable logout
                .and().oauth2Login() // enable OAuth2
                .loginPage("/login").defaultSuccessUrl("/test")
                .and().csrf().disable(); // disable CSRF*/


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
            User user = userDao.findUserByUsername(username).orElseGet(() -> {
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
