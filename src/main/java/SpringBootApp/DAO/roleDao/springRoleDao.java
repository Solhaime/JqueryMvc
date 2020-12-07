package SpringBootApp.DAO.roleDao;

import SpringBootApp.model.Role;
import SpringBootApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface springRoleDao extends JpaRepository<Role, Long> {

    @Query("select role from Role role where role.name =:name")
    Role getRoleByName( @Param("name") String name);
}
