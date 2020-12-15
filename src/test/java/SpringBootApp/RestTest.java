package SpringBootApp;

import SpringBootApp.controllers.RestController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(roles = {"ADMIN"})
@TestPropertySource(locations = "/application-test.properties")
@Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class RestTest {

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private RestController restController;

    @Autowired
    private String jsonTestUser;


    @Test
    public void test() throws Exception {
        assertThat(restController).isNotNull();
    }

    @Test
    public void correctUserCreation() throws Exception {
        MvcResult result = this.mockMvc.perform(post("http://localhost/api/restful/users/create")
                .content(jsonTestUser)
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status()
                .is2xxSuccessful())
//                .andExpect(xpath("//*[@id='allUsersTable']/td").string("testUser"))
                .andReturn();

        assertEquals(200 , result.getResponse().getStatus());

    }

    @Test
    public void constraintUsername() throws Exception {

        MvcResult result1 = this.mockMvc.perform(post("http://localhost/api/restful/users/create")
                .content(jsonTestUser)
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status()
                .is2xxSuccessful())
                .andReturn();

        assertEquals(200 , result1.getResponse().getStatus());

        MvcResult result = this.mockMvc.perform(post("http://localhost/api/restful/users/create")
                .content(jsonTestUser)
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status()
                .is5xxServerError())
                .andReturn();

        assertEquals(500 , result.getResponse().getStatus());
        assertThat(result.getResolvedException().getCause().equals("Constraint username error occurred"));
    }

    @Test
    public void findUserByUsername() throws Exception {
        this.mockMvc.perform(post("http://localhost/api/restful/users/create")
                .content(jsonTestUser)
                .contentType("application/json"));

        MvcResult result = this.mockMvc.perform(get("http://localhost/api/restful/users/byUsername/{username}"
                , "testUser")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status()
                .is2xxSuccessful())
                .andReturn();

        String contentAsString = result.getResponse().getContentAsString();

        assertThat(contentAsString.contains(jsonTestUser));

    }

    @Test
    public void findUserById() throws Exception {
        this.mockMvc.perform(post("http://localhost/api/restful/users/create")
                .content(jsonTestUser)
                .contentType("application/json"));

        MvcResult result = this.mockMvc.perform(get("http://localhost/api/restful/users/{id}"
                , "3")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status()
                .is2xxSuccessful())
                .andReturn();

        String contentAsString = result.getResponse().getContentAsString();

        assertTrue(contentAsString.contains("\"id\":3"));


    }

    @Test
    public void updateUser() throws Exception {

        MvcResult createResult = this.mockMvc.perform(post("http://localhost/api/restful/users/create")
                .content(jsonTestUser)
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status()
                .is2xxSuccessful())
                .andReturn();

        assertEquals(200 , createResult.getResponse().getStatus());


        MvcResult updateResult = this.mockMvc.perform(post("http://localhost/api/restful/users/update")
                .content(jsonTestUser
                .replace("\"username\": \"testUser\""
                ,"\"id\": \"3\",\"username\": \"updatedUser\""))
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status()
                .is2xxSuccessful())
                .andReturn();

        assertEquals(200 , updateResult.getResponse().getStatus());

        MvcResult findByUsernameResult = this.mockMvc.perform(get("http://localhost/api/restful/users/{id}"
                , "3")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status()
                .is2xxSuccessful())
                .andReturn();

        assertEquals(200 , findByUsernameResult.getResponse().getStatus());
        assertThat(findByUsernameResult.getResponse().getContentAsString().contains("updatedUser"));

    }

    @Test
    public void deleteUserById() throws Exception {

        this.mockMvc.perform(post("http://localhost/api/restful/users/create")
                .content(jsonTestUser)
                .contentType("application/json"));

        MvcResult TableBeforeDeleting = this.mockMvc.perform(get("/api/restful/users/all")).andReturn();
        String usersBeforeDelete = TableBeforeDeleting.getResponse().getContentAsString();

        assertTrue(usersBeforeDelete.contains("\"id\":3"));

        MvcResult result = this.mockMvc.perform(delete("http://localhost/api/restful/users/delete/{id}"
                , "3")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status()
                .is2xxSuccessful())
                //.andExpect(xpath("//*[@id='users-list']/table").nodeCount(2))
                .andReturn();

        MvcResult TableAfterDeleting = this.mockMvc.perform(get("/api/restful/users/all"))
                    .andReturn();
        String usersAfterDelete = TableAfterDeleting.getResponse().getContentAsString();

        assertFalse(usersAfterDelete.contains("\"id\":3"));


    }
    @Test
    public void getAllUsersList() throws Exception {
        MvcResult allUsers = this.mockMvc.perform(get("/api/restful/users/all"))
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andReturn();

        MockHttpServletResponse response = allUsers.getResponse();

        assertNotNull(response);
        assertEquals(200, response.getStatus());
    }

    @Test
    public void updateUsersPassword() throws Exception {

        MvcResult createResult = this.mockMvc.perform(post("http://localhost/api/restful/users/create")
                .content(jsonTestUser)
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status()
                        .is2xxSuccessful())
                .andReturn();

        assertEquals(200 , createResult.getResponse().getStatus());

        MvcResult oldPasswordUser = this.mockMvc.perform(get("http://localhost/api/restful/users/{id}"
                , "3")
                .contentType("application/json"))
                .andDo(print())
                .andReturn();


        MvcResult updateResult = this.mockMvc.perform(post("http://localhost/api/restful/users/password")
                .content(jsonTestUser
                        .replace("\"username\": \"testUser\""
                                ,"\"id\": \"3\",\"password\": \"newPassword\""))
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status()
                        .is2xxSuccessful())
                .andReturn();

        assertEquals(200 , updateResult.getResponse().getStatus());

        MvcResult newPasswordUser = this.mockMvc.perform(get("http://localhost/api/restful/users/{id}"
                , "3")
                .contentType("application/json"))
                .andDo(print())
                .andReturn();

        assertNotEquals(oldPasswordUser, newPasswordUser);

    }



}
