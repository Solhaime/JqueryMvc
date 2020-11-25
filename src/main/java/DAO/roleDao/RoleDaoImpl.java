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

    @Override
    public Role getRoleById( long id ) {
      return manager.createQuery("select role from Role role where role.id=:id", Role.class).setParameter("id",id).getSingleResult();
    }

    @Override
    public Collection<Role> getAllRoles() {
        return manager.createQuery("from Role").getResultList();
    }

    @Override
    public boolean deleteRoleById( long id ) {
        return false;
    }
}
