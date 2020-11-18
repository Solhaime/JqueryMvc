package model;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String lastname;


    public User() {
    }

    public User( String name , String lastname ) {
        this.name = name;
        this.lastname = lastname;
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
}
