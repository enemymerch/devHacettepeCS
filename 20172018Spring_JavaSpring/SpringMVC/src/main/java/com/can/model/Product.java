package com.can.model;

import java.io.Serializable;
import java.util.Random;

public class Product implements Serializable{
    private int id;
    private String name;

    public Product(String name) {
        Random idGenerator = new Random();
        this.id = idGenerator.nextInt(1000);
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
