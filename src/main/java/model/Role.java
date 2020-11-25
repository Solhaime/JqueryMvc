package model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private  String role;

/*    @ManyToMany(mappedBy = "roles")
    private Set<User> users;*/

    public Role() {
    }

    public Role( String role ) {
        this.role =role;
    }

    @Override
    public String getAuthority() {
        return role;
    }

    public void setRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }

    public Long getId() {
        return id;
    }
}
