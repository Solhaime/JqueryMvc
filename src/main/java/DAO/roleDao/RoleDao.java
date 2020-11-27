package DAO.roleDao;

import model.Role;

import java.util.Collection;

public interface RoleDao {
    void addRole(String role);

    Role getRoleById( long id);

    Collection<Role> getAllRoles();

    Role getRoleByName( String role);

}
