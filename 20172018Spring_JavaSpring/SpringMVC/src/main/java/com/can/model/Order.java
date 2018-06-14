package com.can.model;

import java.io.Serializable;
import java.sql.Time;
import java.util.Random;

public class Order implements Serializable{
    private int id;
    private String product;
    private int piece;
    private String username;
    private String status;

    public Order(String product, int piece, String username){
        Random idGenerator = new Random();
        this.id = idGenerator.nextInt(100000) + 90000;
        this.product = product;
        this.piece = piece;
        this.username= username;
        this.status = "Order received";
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getPiece() {
        return piece;
    }

    public void setPiece(int piece) {
        this.piece = piece;
    }
}
