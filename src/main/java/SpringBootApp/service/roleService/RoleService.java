package SpringBootApp.service.roleService;

import SpringBootApp.model.Role;

import java.util.Collection;
import java.util.List;

public interface RoleService {

    void addRole(String role);

    Role getRoleById(long id);

    Collection<Role> getAllRoles();

    Role getRoleByName( String role);

    public void springDataSave( Role role );

    public void springDataSave( String role );

    public Role springDataGetById( Long id );

    public List<Role> springDataFindAll();

    public Role springDataGetByName( String name );

}
