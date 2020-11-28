package SpringBootApp.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String lastname;

    @Column(unique = true, nullable = false)
    private String username;


    private String password;

    private boolean isActive = true;


    @ManyToMany(cascade =
            CascadeType.ALL, fetch= FetchType.LAZY)
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User( String name , String lastname , String username , String password) {
        this.name = name;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
    }

    public User( String name , String lastname , String username , String password, String role ) {
        this.name = name;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.roles.add(new Role(role));
    }
    public User( String name , String lastname , String username , String password , Set<Role> roles ) {
        this.name = name;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public long getId() {
        return id;
    }

    public void setId( long id ) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname( String lastname ) {
        this.lastname = lastname;
    }

    public void setUsername( String username ) {
        this.username = username;
    }

    public void setPassword( String password ) {
        this.password = password;
    }

    public void addRole(String role){
        roles.add(new Role(role));
    }

    public void setRole(Role role){
        roles.add(role);
    }

    public void changeRole(String role){
        this.roles.clear();
        this.roles.add(new Role(role));
    }

    public String isActiveString(){
        return String.valueOf(isActive);
    }

    public String getRolesString(){
        StringBuilder result=new StringBuilder();
        for(Role r: roles){
            result.append(r.getAuthority()+", ");
        }
        result.delete(result.length()-2, result.length()-1);
        return result.toString();
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive( boolean active ) {
        isActive = active;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + getRolesString() +
                '}';
    }
}
