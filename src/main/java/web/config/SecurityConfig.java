package web.config;


import model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import service.userService.UserDetailServiceImpl;
import web.handler.SecondAuthenticationSuccessHandler;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@ComponentScan({"DAO","service"})
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailServiceImpl userService;


    @Autowired
    public SecurityConfig( UserDetailServiceImpl userService ) {
        this.userService = userService;
    }

    @Bean
    public AuthenticationSuccessHandler secondAuthenticationSuccessHandler(){
        return new SecondAuthenticationSuccessHandler();
    }


    /*@Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return new MyAuthenticationSuccessHandler();
    }*/

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(provider());/*userDetailsService(userService).passwordEncoder(passwordEncoder());*/
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()/*.authorizeRequests().antMatchers("/index").permitAll()
                .and()*//*.authorizeRequests().antMatchers("/admin/**").hasAuthority("ADMIN")
                .and()*/.formLogin().loginPage("/login").loginProcessingUrl("/login")
                .successHandler(secondAuthenticationSuccessHandler()).and().exceptionHandling().accessDeniedPage("/accessDenied")
                    .and().logout();/*formLogin().loginPage("/login").defaultSuccessUrl("/courses",true).and()
                .antMatcher("/login").anonymous()*/
               /* .and().rememberMe()*/
     /*               .and().logout().logoutUrl("/logout").clearAuthentication(true).invalidateHttpSession(true).deleteCookies("JSESSION","remember-me")
                .logoutSuccessUrl("/login")
                .and().antMatcher("/register").anonymous()
                .and()
                .exceptionHandling().accessDeniedPage("/accessDenied");*/
/*
        http .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/home").permitAll() // (3)
                .anyRequest().authenticated() // (4)
                .and()
                .formLogin() // (5)
                .loginPage("/login") // (5)
                .permitAll().defaultSuccessUrl("/user")
                .and()
                .logout() // (6)
                .permitAll()
                .and().antMatcher("/register").anonymous()
                .and()
                .formLogin();*/


      /*  http
                // делаем страницу регистрации недоступной для авторизированных пользователей
                .authorizeRequests()
                //страницы аутентификаци доступна всем
                .antMatchers("/login").anonymous()
                // защищенные URL
                .antMatchers("/").access("hasAnyRole('ADMIN')").anyRequest().authenticated();*/


    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public DaoAuthenticationProvider provider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userService);
        return provider;
    }
}
