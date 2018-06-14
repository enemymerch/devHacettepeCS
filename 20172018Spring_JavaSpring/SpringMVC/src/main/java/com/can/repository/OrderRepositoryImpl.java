package com.can.repository;

import com.can.model.Order;
import com.can.model.Product;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class OrderRepositoryImpl implements OrderRepository{
    private String orderFile = "orders.ser";

    public void add(Order order) {
        ArrayList<Order> orders=  getOrders();
        orders.add(order);
        setProducts(orders);
    }

    public void update(int id, String status) {
        ArrayList<Order> orders =  getOrders();
        for(int i = 0; i < orders.size();i++){
            Order order = orders.get(i);
            if (order.getId() == id){
                order.setStatus(status);
                orders.set(i, order);
                setProducts(orders);
                break;
            }
        }
    }


    public ArrayList<Order> getOrders() {
        try {
            FileInputStream fileInputStream = new FileInputStream(this.orderFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            ArrayList<Order> orders = (ArrayList<Order>) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
            return orders;
        } catch (Exception e) {
            e.printStackTrace();
            return  new ArrayList<Order>();
        }
    }

    public void setProducts(ArrayList<Order> order) {
        try {
            FileOutputStream fileOutputStream = null;
            fileOutputStream = new FileOutputStream(this.orderFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(order);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
