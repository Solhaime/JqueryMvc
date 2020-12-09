package SpringBootApp.controllers;

import SpringBootApp.EntryPoint;
import SpringBootApp.model.User;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;


//@TestPropertySource(locations = "classpath:application.properties")
@RunWith(SpringRunner.class)
@ContextConfiguration
@SpringBootTest(classes = EntryPoint.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RestControllerTest {

    Logger logger = LoggerFactory.getLogger(this.getClass());

/*    @Autowired
    private TestService testService;*/

   // @Value("${server.port}")
    private int port =8080;

    private String urlStartingString = "http://localhost:";

    private String classBasedRequestMapping = "/api/restful/users";

    private String path = String.format("%s%d%s" , urlStartingString , port , classBasedRequestMapping);

    @Autowired
    TestRestTemplate template;

    HttpHeaders headers = new HttpHeaders();


    @WithUserDetails(value = "admin",userDetailsServiceBeanName = "detailService")
    @Test
    void add() {
/*        String url = "http://localhost:8080/login";
        template.getForEntity(url, String.class);
        HttpHeaders sessionHeaders = template.headForHeaders(url);
        headers.put("Cookie", sessionHeaders.get("Cookie"));
        headers.put("Content-Type", sessionHeaders.get("Content-Type"));*/

        String createMethodEndPoint = "/create";
        String createMethodPath = String.format("%s%s" , path , createMethodEndPoint);
/*        List<String> contentType = new ArrayList<>();
        contentType.add("application/json");
        headers.put("Content-Type", contentType );*/

       User user = new User("Douglas" , "Hofstadter" , "GEB@mail.com" , "pulitzer" , "[ADMIN]");
        //String userich = "{\"name\": \"a\", \"lastname\": \"a\", \"username\": \"a\", \"password\": \"a\", \"roles\": [\"ADMIN\"]}";
        HttpEntity<User> addingUserRequest = new HttpEntity<>(user, headers);
        ResponseEntity<Void> retrieveAddingResponse = template.exchange(createMethodPath , HttpMethod.POST , addingUserRequest , Void.class);
        int creatingUserStatusCode = retrieveAddingResponse.getStatusCode().value();
        Assert.assertEquals("Correct status code returned" , 200 , creatingUserStatusCode);
        logger.info(String.format("%s %d" , "Returned status code of creating new User is" , creatingUserStatusCode));
    }

    @Test
    void getById() {
    }

    @Test
    void update() {
    }

    @Test
    void getByUsername() {
    }

    @Test
    void changePassword() {
    }

    @Test
    void delete() {
    }

    @Test
    void getAll() {
    }

}