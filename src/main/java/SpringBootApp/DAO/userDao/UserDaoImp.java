package SpringBootApp.DAO.userDao;

import SpringBootApp.model.User;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository("daoRep")
@ComponentScan("SpringBootApp.config")
public class UserDaoImp implements UserDao{

    @PersistenceContext
    private EntityManager em;



    @Override
    public List<User> listUsers() {
        return em.createQuery("from User").getResultList();

    }

    @Override
    public void addUser(User user) {
        em.persist(user);
    }


    @Override
    public User  getUserByUsername( String username ) {
        User user = (User) em.createQuery("select user from User user JOIN FETCH user.roles where user.username =:username").setParameter("username", username).getSingleResult();
        return user;
    }

    @Override
    public void deleteUserById(Long id){
            em.createQuery("delete from User user where user.id=:id").setParameter("id" , id).executeUpdate();
        }

    @Override
    public void updateUserDetails( boolean isActive, Long id ){
        Query query = em.createQuery("update User  user set user.isActive=:isActive where user.id=:id");
        query.setParameter("id",id)
                .setParameter("isActive", isActive).executeUpdate();

    }

    @Override
    public User  getUserById( Long id ) {
        User user = (User) em.find(User.class, id);
        return user;
    }

    @Override
    public void mergeUser(User user){
        em.merge(user);
    }


}
