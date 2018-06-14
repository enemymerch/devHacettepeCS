package com.can.service;

import com.can.model.Customer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface UserService {
    boolean isCustomer(String username, String password);
    boolean isOwner(String username, String password);
    ArrayList<Customer> getCustomers();
    String addCustomer(String username, String password, String name, String surname, String floor, String building, String room);
    String updateCustomer(int id, String name, String surname, String floor, String building, String room);
    boolean doesCustomerExitsWithUsername(String username);
    Customer getCustomerByUsername(String username);
    String updateCustomerPassword(String username, String oldPassword, String newPassword, String newPasswordAgain);
}
