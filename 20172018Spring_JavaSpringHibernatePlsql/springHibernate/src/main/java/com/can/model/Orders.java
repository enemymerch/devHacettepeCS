package com.can.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="Orders")
public class Orders implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private String product;

    @Column
    private int piece;

    @Column
    private String username;

    @Column
    private String status;


    public String getUsername() {
        return username;
    }
    public String getStatus() {
        return status;
    }
    public int getId() {
        return id;
    }
    public String getProduct() {
        return product;
    }
    public int getPiece() {
        return piece;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setProduct(String product) {this.product = product;}
    public void setPiece(int piece) { this.piece = piece;}

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Orders other = (Orders) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }


}
