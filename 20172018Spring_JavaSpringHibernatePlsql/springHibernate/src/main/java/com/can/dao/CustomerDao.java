package com.can.dao;

import com.can.model.Customer;
import com.can.model.Orders;

import java.util.List;

public interface CustomerDao {
    public void addCustomer(Customer customer);
    public void updateCustomer(Customer customer);
    public List<Customer> getCustomers();
    public Customer getCustomerById(int id);
    public void removeCustomer(int id);
}
