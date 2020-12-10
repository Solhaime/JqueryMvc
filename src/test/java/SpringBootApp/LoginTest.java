package SpringBootApp;

import SpringBootApp.controllers.HelloController;
import SpringBootApp.controllers.RestController;
import SpringBootApp.service.userService.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LoginTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HelloController helloController;

    @Test
    public void test() throws Exception{
        assertThat(helloController).isNotNull();
    }

    @Test
    public void accessDeniedTest() throws Exception{
        this.mockMvc.perform(get("/test"))
                .andDo(print()).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    public void correctLoginTest() throws Exception{
        this.mockMvc.perform(formLogin().loginProcessingUrl("/loginMain").user("admin").password("admin"))
                    .andDo(print()).andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/test"));
    }

    @Test
    public void badCredentials() throws Exception{
        this.mockMvc.perform(formLogin().loginProcessingUrl("/loginMain").user("sobakaTakaya").password("pochemuNasrala"))
                .andDo(print()).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/login?error"));
    }

}
