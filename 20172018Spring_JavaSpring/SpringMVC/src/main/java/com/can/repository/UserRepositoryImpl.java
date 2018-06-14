package com.can.repository;

import com.can.model.Customer;
import com.can.model.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository{

    private String customerFile = "customer.ser";

    public UserRepositoryImpl(){
        try {
            File file = new File(this.customerFile);
            if (!file.exists()){
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                ObjectOutputStream  objectOutputStream = new ObjectOutputStream(fileOutputStream);
                ArrayList<Customer> customers = new ArrayList<Customer>();
                objectOutputStream.writeObject(customers);
                objectOutputStream.close();
                fileOutputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addCustomer(Customer param) {

    }

    public void updateCustomer(Customer param1, Customer param2) {

    }

    public void deleteCustomer(Customer param) {

    }

    public void setCustomers(ArrayList<Customer> customers){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(this.customerFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(customers);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public ArrayList<Customer> getCustomers() {
        try {
            FileInputStream fileInputStream = new FileInputStream(this.customerFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            ArrayList<Customer> customers = (ArrayList<Customer>) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
            return customers;
        } catch (Exception e) {
            e.printStackTrace();
            return  new ArrayList<Customer>();
        }
    }
}
