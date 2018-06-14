package com.can.repository;

import com.can.model.Customer;
import com.can.model.Product;
import com.can.service.ProductService;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class ProductRepositoryImpl implements ProductRepository {
    private String productsFile = "products.ser";

    public ProductRepositoryImpl(){
        File file = new File(this.productsFile);
        if (!file.exists()){
            setProducts(new ArrayList<Product>());
        }
    }
    public void add(Product param) {
        ArrayList<Product> products =  getProducts();
        products.add(param);
        setProducts(products);
    }

    public void update(int id, String name) {
        ArrayList<Product> products =  getProducts();
        for(int i = 0; i < products.size();i++){
            Product product = products.get(i);
            if (product.getId() == id){
                product.setName(name);
                products.set(i, product);
                setProducts(products);
                break;
            }
        }
    }

    public void delete(int id) {
        ArrayList<Product> products =  getProducts();
        for(int i = 0; i < products.size();i++){
            Product product = products.get(i);
            if (product.getId() == id){
                products.remove(i);
                setProducts(products);
                break;
            }
        }
    }
    public void setProducts(ArrayList<Product> products){
        try {
            FileOutputStream fileOutputStream = null;
            fileOutputStream = new FileOutputStream(this.productsFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(products);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Product> getProducts() {
        try {
            FileInputStream fileInputStream = new FileInputStream(this.productsFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            ArrayList<Product> products = (ArrayList<Product>) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
            return products;
        } catch (Exception e) {
            e.printStackTrace();
            return  new ArrayList<Product>();
        }
    }

    public Product getProductByID(int id){
        ArrayList<Product> products = getProducts();
        for(int i = 0; i < products.size(); i++){
            if(products.get(i).getId() == id){
                return products.get(i);
            }
        }
        return null;
    }

    public Product getProductByName(String name){
        ArrayList<Product> products = getProducts();
        for(int i = 0; i < products.size(); i++){
            if(products.get(i).getName().equals(name)){
                return products.get(i);
            }
        }
        return null;
    }
}
