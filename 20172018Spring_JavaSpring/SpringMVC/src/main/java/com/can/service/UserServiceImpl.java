package com.can.service;

import com.can.model.Address;
import com.can.model.Customer;
import com.can.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    public boolean isCustomer(String username, String password) {
        ArrayList<Customer> customers = userRepository.getCustomers();
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
        return userRepository.getCustomers();
    }


    public String addCustomer(String username, String password, String name, String surname, String floor, String building, String room) {
        String message = "Customer username is not unique";
        ArrayList<Customer> customers = userRepository.getCustomers();
        boolean doesCustomerUsernameExits = false;
        for(Customer customer:customers){
            if(customer.getUsername().equals(username)){
                doesCustomerUsernameExits = true;
                break;
            }
        }
        if(!doesCustomerUsernameExits){
            customers.add(new Customer(username, password, name, surname, new Address(floor, building, room)));
            userRepository.setCustomers(customers);
            message = "Customer added";
        }
        return message;
    }

    public String updateCustomer(int id, String name, String surname, String floor, String building, String room) {
        String message = "Customer id not correct";
        ArrayList<Customer> customers = userRepository.getCustomers();
        for(int i = 0; i< customers.size(); i++){
            Customer customer = customers.get(i);
            if(customer.getId() == id){
                Address address = customer.getAddress();
                if (!name.trim().equals("")){
                    customer.setName(name);
                }
                if (!surname.trim().equals("")){
                    customer.setSurname(surname);
                }
                if (!floor.trim().equals("")){
                    address.setFloor(floor);
                }
                if (!building.trim().equals("")){
                    address.setBuilding(building);
                }
                if (!room.trim().equals("")){
                    address.setRoom(room);
                }
                customer.setAddress(address);
                customers.set(i, customer);
                userRepository.setCustomers(customers);
                message = "Customer updated";
                break;
            }
        }
            return message;
    }

    public boolean doesCustomerExitsWithUsername(String username) {
        ArrayList<Customer> customers = userRepository.getCustomers();
        for (Customer customer:customers){
            if(customer.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    public Customer getCustomerByUsername(String username) {
        ArrayList<Customer> customers = userRepository.getCustomers();
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
            ArrayList<Customer> customers = userRepository.getCustomers();
            for(int i = 0 ; i < customers.size(); i++){
                Customer customer = customers.get(i);
                if(customer.getUsername().equals(username)){
                    customer.setPassword(newPassword);
                    customers.set(i, customer);
                    userRepository.setCustomers(customers);
                    message = "Password updated";
                    break;
                }
            }
        }
        return message;
    }
}
