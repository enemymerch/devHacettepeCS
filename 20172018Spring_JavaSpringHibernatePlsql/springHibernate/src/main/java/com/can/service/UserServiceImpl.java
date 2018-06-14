package com.can.service;

import com.can.dao.CustomerDao;
import com.can.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class UserServiceImpl implements UserService{

    @Autowired
    CustomerDao customerDao;

    public boolean isCustomer(String username, String password) {
        ArrayList<Customer> customers = (ArrayList<Customer>) customerDao.getCustomers();
        boolean isCustomer = false;
        for (Customer customer : customers) {
            if (customer.getUsername().equals(username) && customer.getPassword().equals(password)){
                isCustomer = true;
            }
        }
        return isCustomer;
    }

    public boolean isOwner(String username, String password) {
        if(username.equals("admin") && password.equals("1234")){
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<Customer> getCustomers() {
        return (ArrayList<Customer>) customerDao.getCustomers();
    }

    public String addCustomer(String username, String password, String name, String surname, String floor, String building, String room) {
        String message = "Customer username is not unique";
        ArrayList<Customer> customers = (ArrayList<Customer>) customerDao.getCustomers();
        boolean doesCustomerUsernameExits = false;
        for(Customer customer:customers){
            if(customer.getUsername().equals(username)){
                doesCustomerUsernameExits = true;
                break;
            }
        }
        if(!doesCustomerUsernameExits){
            Customer customer = new Customer();
            customer.setUsername(username);
            customer.setPassword(password);
            customer.setName(name);
            customer.setSurname(surname);
            customer.setFloor(floor);
            customer.setBuilding(building);
            customer.setRoom(room);
            customerDao.addCustomer(customer);
            message = "Customer added";
        }
        return message;
    }

    public String updateCustomer(int id, String name, String surname, String floor, String building, String room) {
        String message = "Customer id not correct";
        Customer customer = customerDao.getCustomerById(id);
        if(customer!= null){

            if (!name.trim().equals("")){
                customer.setName(name);
            }
            if (!surname.trim().equals("")){
                customer.setSurname(surname);
            }
            if (!floor.trim().equals("")){
                customer.setFloor(floor);
            }
            if (!building.trim().equals("")){
                customer.setBuilding(building);
            }
            if (!room.trim().equals("")){
                customer.setRoom(room);
            }
            customerDao.updateCustomer(customer);
            message = "Customer updated";
        }
        return message;
    }

    public boolean doesCustomerExitsWithUsername(String username) {
        ArrayList<Customer> customers = (ArrayList<Customer>) customerDao.getCustomers();
        for (Customer customer:customers){
            if(customer.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    public Customer getCustomerByUsername(String username) {
        ArrayList<Customer> customers = (ArrayList<Customer>) customerDao.getCustomers();
        for(Customer customer:customers){
            if(customer.getUsername().equals(username)){
                return customer;
            }
        }
        return null;
    }

    public String updateCustomerPassword(String username, String oldPassword, String newPassword, String newPasswordAgain) {
        String message = "";
        if (oldPassword.equals(newPassword)){
            message = "Old password and new password are the same";
        } else if (!newPassword.equals(newPasswordAgain)){
            message = "New passwords are different";
        } else if ( !doesCustomerExitsWithUsername(username)){
            message = "Cannot find user";
        } else {
            ArrayList<Customer> customers = (ArrayList<Customer>) customerDao.getCustomers();
            for(int i = 0 ; i < customers.size(); i++){
                Customer customer = customers.get(i);
                if(customer.getUsername().equals(username)){
                    customer.setPassword(newPassword);
                    customerDao.updateCustomer(customer);
                    message = "Password updated";
                    break;
                }
            }
        }
        return message;
    }
}
