package SpringBootApp;

import SpringBootApp.controllers.HelloController;
import SpringBootApp.controllers.RestController;
import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.catchThrowableOfType;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RestTest {

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private RestController restController;

    @Test
    public void test() throws Exception{
        assertThat(restController).isNotNull();
    }

    @Test
    @WithUserDetails(value = "admin")
    public void correctUserCreation() throws Exception{
        this.mockMvc.perform(post("http://localhost/api/restful/users/create")
                .content("{\"name\": \"a\", \"lastname\": \"a\", \"username\": \"a\", \"password\": \"a\", \"roles\": [\"ADMIN\"]}")
                .contentType("application/json")).andDo(print()).andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithUserDetails(value = "admin")
    public void constraintUsername() throws Exception{
        MvcResult result = this.mockMvc.perform(post("http://localhost/api/restful/users/create")
                .content("{\"name\": \"user\", \"lastname\": \"user\", \"username\": \"user\", \"password\": \"user\", \"roles\": [\"USER\"]}")
                .contentType("application/json")).andDo(print()).andExpect(status().is5xxServerError()).andReturn();
        assertThat(result.getResolvedException().getCause().equals("Constraint username error occurred"));

    }

    @Test
    @WithUserDetails(value = "admin")//TODO застопился на том добавлять и удалять ли юзера каждый раз
    public void correctUserUpdate() throws Exception{
        this.mockMvc.perform(post("http://localhost/api/restful/users/create")
                .content("{\"name\": \"findByUsernameTest\",    " +
                        " \"lastname\": \"findByUsernameTest\"," +
                        "    \"username\": \"findByUsernameTest\", " +
                        "   \"password\": \"findByUsernameTest\", \"roles\": [\"ADMIN\"]}")
                .contentType("application/json"));
        MvcResult result = this.mockMvc.perform(post("http://localhost/api/restful/users/byUsername/{username}").requestAttr("username" , "findByUsernameTest")
                .contentType("application/json")).andDo(print()).andExpect(status().is2xxSuccessful()).andReturn();
        result.getResponse().getContentAsString();
    }

//    this.mockMvc.perform(post("http://localhost/api/restful/users/byUsername/{username}")
//                .content("{\"id\": \"a\",\"name\": \"a\", \"lastname\": \"a\", \"username\": \"a\", \"password\": \"a\", \"roles\": [\"ADMIN\"]}")
//                .contentType("application/json")).andDo(print()).andExpect(status().is2xxSuccessful());
}
