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
    private  String name;

    public Role() {
    }

    public Role( String role ) {
        this.name =role;
    }

    @Override
    public String getAuthority() {
        return name;
    }

    public void setName( String role){
        this.name = role;
    }

    public String getName(){
        return name;
    }

    public Long getId() {
        return id;
    }
}
