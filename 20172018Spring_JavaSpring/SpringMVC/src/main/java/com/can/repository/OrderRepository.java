package com.can.repository;
import org.springframework.stereotype.Repository;
import com.can.model.Order;

import java.util.ArrayList;

@Repository
public interface OrderRepository{
    void add(Order order);
    void update(int id, String status);
    ArrayList<Order> getOrders();
    void setProducts(ArrayList<Order> products);
}
