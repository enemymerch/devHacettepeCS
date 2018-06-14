package com.can.dao;

import com.can.model.Orders;

import java.util.List;

public interface OrderDao {
    public void addOrder(Orders orders);
    public void updateOrder(Orders orders);
    public List<Orders> getOrders();
    public Orders getOrderById(int id);
    public void removeOrder(int id);
}
