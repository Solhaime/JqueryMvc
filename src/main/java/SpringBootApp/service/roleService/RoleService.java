package SpringBootApp.service.roleService;

import SpringBootApp.model.Role;

import java.util.Collection;

public interface RoleService {

    void addRole(String role);

    Role getRoleById(long id);

    Collection<Role> getAllRoles();

    Role getRoleByName( String role);
}
