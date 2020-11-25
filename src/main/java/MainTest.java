import config.AppConfig;
import model.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import org.springframework.security.core.userdetails.UserDetailsService;
import service.userService.UserDetailServiceImpl;
import service.userService.UserService;

public class MainTest {
    public static void main( String[] args ) {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);
        UserService bean = context.getBean(UserService.class);
        UserDetailsService service = context.getBean(UserDetailServiceImpl.class);
//        bean.add(new User("Bek", "Bek","bek", "bek"));
   /*     List<User> users = bean.listUsers();
        for(User user : users) {
            System.out.println("Id = " + user.getId());
            System.out.println("First Name = " + user.getName());
            System.out.println("Last Name = " + user.getLastname());*/
           /*User user = (User) service.loadUserByUsername("bek");*/
        /*    bean.setUserRoleWhereRoleId((long)1);*/
        String role = "ADMIN";
        Long id = 31L;
        User user = bean.getUserById(id);
        user.addRole(role);
        bean.mergeUser(user);
  /*      bean.updateUserDetails(true,role ,(long)31);*/

/*        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails) {
          String username =  ((UserDetails)principal).getUsername();*/
/*            User user = bean.getUser(username);
            for(User user1 : user) {
                System.out.println("Id = " + user1.getId());
                System.out.println("First Name = " + user1.getName());
                System.out.println("Last Name = " + user1.getLastname());
        }*/
        }
    }

