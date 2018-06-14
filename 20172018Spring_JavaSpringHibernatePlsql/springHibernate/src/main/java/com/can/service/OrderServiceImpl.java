package com.can.service;


import com.can.dao.OrderDao;
import com.can.model.Orders;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDao orderDao;
    @Autowired
    UserService userService;
    @Autowired
    ProductService productService;

    public ArrayList<Orders> getAll() {
        return (ArrayList<Orders>)orderDao.getOrders();
    }

    public ArrayList<Orders> getOrdersByUsername(String username) {
        ArrayList<Orders> orders = new ArrayList<Orders>();
        for(Orders order: orderDao.getOrders()){
            if(order.getUsername().equals(username)){
                orders.add(order);
            }
        }
        return orders;
    }

    public String addOrder(String customerUsername, String productName, int piece) {
        String message = "Orders cannot be added";
        if (userService.doesCustomerExitsWithUsername(customerUsername) && productService.doesProductExitsWithName(productName)){
            Orders orders = new Orders();
            orders.setProduct(productName);
            orders.setStatus("Orders received");
            orders.setPiece(piece);
            orders.setUsername(customerUsername);
            orderDao.addOrder(orders);
            message = "Orders added";
        }
        return message;
    }

    public String updateOrder(int id, String status) {
        String message = "Orders cannot be updated";
        Orders orders = orderDao.getOrderById(id);
        if(orders.getId() == id){
            orders.setStatus(status);
            orderDao.updateOrder(orders);
            message = "Orders updated";
        }
        return message;
    }
}
