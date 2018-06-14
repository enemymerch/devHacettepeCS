package com.can.repository;

import com.can.model.Customer;
import com.can.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public  interface UserRepository{
    void addCustomer(Customer param);
    void updateCustomer(Customer param1, Customer param2);
    void deleteCustomer(Customer param);
    ArrayList<Customer> getCustomers();
    void setCustomers(ArrayList<Customer> customers);
}
