package com.can.model;

import java.io.Serializable;

public class Address implements Serializable{
    private String floor;
    private String building;
    private String room;

    public Address(String floor, String building, String room) {
        this.floor = floor;
        this.building = building;
        this.room = room;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
