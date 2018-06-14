package com.can.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="Customer")
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private String floor;

    @Column
    private String building;

    @Column
    private String room;
    /*
    * Getters
    */
    public Integer getId() {return id; }
    public String getUsername() {return username;}
    public String getPassword() {return password;}
    public String getName() { return name;}
    public String getSurname() {return surname;}
    public String getFloor(){return floor;}
    public String getBuilding(){return building;}
    public String getRoom(){return room;}

    public void setId(Integer id) {this.id = id;}
    public void setUsername(String username) {this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setName(String name) { this.name = name; }
    public void setSurname(String surname) {this.surname = surname;}
    public void setFloor(String floor){this.floor = floor;}
    public void setBuilding(String building){this.building = building;}
    public void setRoom(String room){this.room = room;}

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Customer other = (Customer) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
