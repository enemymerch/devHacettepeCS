package com.can.service;

        import com.can.model.Orders;
        import org.springframework.stereotype.Service;

        import java.util.ArrayList;

@Service
public interface OrderService {
    ArrayList<Orders> getAll();
    ArrayList<Orders> getOrdersByUsername(String username);
    String addOrder(String customerUsername, String productName, int piece);
    String updateOrder(int id, String status);
}
