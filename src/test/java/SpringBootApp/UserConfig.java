package SpringBootApp;

import SpringBootApp.model.Role;
import SpringBootApp.model.User;


import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class UserConfig {


    @Bean
    public String jsonTestUser(){
        return "{\"name\": \"testUser\", \"lastname\": \"testUser\"  " +
                ", \"username\": \"testUser\", \"password\": \"testUser\", \"roles\": [\"ADMIN\"]}";
    }


//    @Bean
//    public User testUserInstance(){
//       User user = new User();
//       user.setId(3);
//       user.setName("testUser");
//       user.setLastname("testUser");
//       user.setUsername("testUser");
//       user.setPassword("testUser");
//       return user;
//    }
//
//    @Bean
//    public Set<Role> testUserRoles(){
//        Set<Role> roles = new HashSet<>();
//        roles.add(new Role("USER"));
//        roles.add(new Role("ADMIN"));
//        return roles;
//    }
//
//    @Bean
//    public String jsonSerializer() throws IOException {
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonUser = mapper.writeValueAsString(testUserInstance());
//        return jsonUser.concat(", \"roles\": [\"ADMIN\"]");
//    }
}
