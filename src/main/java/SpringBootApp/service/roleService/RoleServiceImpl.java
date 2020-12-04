package SpringBootApp.service.roleService;

import SpringBootApp.DAO.roleDao.RoleDao;
import SpringBootApp.DAO.roleDao.springRoleDao;
import SpringBootApp.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private springRoleDao springDataRole;

    @Override
    public void addRole( String role ) {
        roleDao.addRole(role);
    }

    @Override
    public Role getRoleById( long id ) {
        return roleDao.getRoleById(id);
    }

    @Override
    public Collection<Role> getAllRoles() {
        return roleDao.getAllRoles();
    }

    @Override
    public Role getRoleByName( String role ) {
        return roleDao.getRoleByName(role);
    }


    //Spring Data

    public void springDataSave( Role role ) {
        springDataRole.save(role);
    }

    public void springDataSave( String role ) {
        springDataRole.save(new Role(role));
    }

    public Role springDataGetById( Long id ) {
        return springDataRole.findById(id).get();
    }

    public List<Role> springDataFindAll() {
        return springDataRole.findAll();
    }

    public Role springDataGetByName( String name ) {
        return springDataRole.getRoleByName(name);
    }

}
