package com.can.model;

import java.io.Serializable;
import java.util.Random;

public class Customer implements Serializable {
    private int id;
    private String username;
    private String password;
    private String name;
    private String surname;
    private Address address;

    public Customer(String username, String password, String name, String surname, Address address) {
        Random idGenerator = new Random();
        this.id  = idGenerator.nextInt(20000)+10000;
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
