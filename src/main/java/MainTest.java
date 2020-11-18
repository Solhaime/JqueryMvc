import config.AppConfig;
import model.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import service.UserService;

import java.util.List;

public class MainTest {
    public static void main( String[] args ) {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);
        UserService bean = context.getBean(UserService.class);
        bean.add(new User("Andrey" , "Lolokov"));
        List<User> users = bean.listUsers();
        for(User user : users) {
            System.out.println("Id = " + user.getId());
            System.out.println("First Name = " + user.getName());
            System.out.println("Last Name = " + user.getLastname());

        }
    }
}
