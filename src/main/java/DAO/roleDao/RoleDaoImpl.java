package DAO.roleDao;

import model.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
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

    @Override//find
    public Role getRoleById( long id ) {
      return manager.find(Role.class,id);
    }

    @Override
    public Collection<Role> getAllRoles() {
        return manager.createQuery("from Role").getResultList();
    }

}
