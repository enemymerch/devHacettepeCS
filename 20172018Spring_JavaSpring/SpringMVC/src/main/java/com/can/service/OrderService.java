package com.can.service;

import com.can.model.Order;
import com.can.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.ArrayList;

@Service
public interface OrderService {
    ArrayList<Order> getAll();
    ArrayList<Order> getOrdersByUsername(String username);
    String addOrder(String customerUsername, String productName, int piece);
    String updateOrder(int id, String status);

}
