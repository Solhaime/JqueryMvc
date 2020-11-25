package DAO.userDao;

import model.Role;
import model.User;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository("daoRep")
@ComponentScan("config")
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
        User user = (User) em.createQuery("select user from User user where user.username =:username").setParameter("username", username).getSingleResult();
        return user;
    }

    @Override
    public void deleteUserById(Long id){
            em.createQuery("delete from User user where user.id=:id").setParameter("id" , id).executeUpdate();
        }

    @Override
    public void updateUserDetails( boolean isActive , Role role , Long id ){
        Query query = em.createQuery("update User  set  " +
                "isActive=:isActive, roles=:role where id=:id");
        query.setParameter("id",id)
                .setParameter("isActive", isActive)
                .setParameter("role", role).executeUpdate();
    }

  /*  public void setUserRoleWhereRoleId(Long id){
        em.createQuery("insert into User(roles) Role role where role.id=:id").setParameter("id",id);
    }*/
/*    @Override
    public Optional<User> selectUserByUsername( String username ) {
        return listUsers().stream().filter(user -> username.equals(user.getUsername())).findFirst();
    }*/

/*    @Override
    public User getUserByUsername( String username ) {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("select user from User user where user.username =:username");
        query.setParameter("username", username);
        if (query.getSingleResult() == null) {
            throw new UsernameNotFoundException(username);
        }
        return query.getSingleResult();
    }*/
}
