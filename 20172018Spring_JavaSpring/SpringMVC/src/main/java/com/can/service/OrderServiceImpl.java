package com.can.service;


import com.can.model.Order;
import com.can.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserService userService;
    @Autowired
    ProductService productService;

    public ArrayList<Order> getAll() {
        return orderRepository.getOrders();
    }

    public ArrayList<Order> getOrdersByUsername(String username) {
        ArrayList<Order> orders = new ArrayList<Order>();
        for(Order order: orderRepository.getOrders()){
            if(order.getUsername().equals(username)){
                orders.add(order);
            }
        }
        return orders;
    }

    public String addOrder(String customerUsername, String productName, int piece) {
        String message = "Order cannot be added";
        if (userService.doesCustomerExitsWithUsername(customerUsername) && productService.doesProductExitsWithName(productName)){
            orderRepository.add(new Order(productName, piece, customerUsername));
            message = "Order added";
        }
        return message;
    }

    public String updateOrder(int id, String status) {
        String message = "Order cannot be updated";
        ArrayList<Order> orders = orderRepository.getOrders();
        for(int i = 0 ; i < orders.size() ; i++){
            Order order = orders.get(i);
            if(order.getId() == id){
                order.setStatus(status);
                orders.set(i, order);
                orderRepository.setProducts(orders);
                message = "Order updated";
                break;
            }
        }
        return message;
    }
}
