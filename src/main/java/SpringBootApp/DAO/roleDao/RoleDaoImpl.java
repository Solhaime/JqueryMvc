package SpringBootApp.DAO.roleDao;

import SpringBootApp.model.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Collection;
@Repository
public class RoleDaoImpl implements RoleDao{

    @PersistenceContext
    EntityManager manager;

    @Override
    public void addRole( String role ) {
        manager.persist(new Role(role));
    }

    @Override
    public Role getRoleById( long id ) {
      return manager.find(Role.class,id);
    }



    @Override
    public Collection<Role> getAllRoles() {
        return manager.createQuery("from Role").getResultList();
    }

    @Override
    public Role getRoleByName( String role ) {
        try {
            return manager.createQuery("select role from Role role where role.name=:name" , Role.class).setParameter("name" , role).getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }

}
