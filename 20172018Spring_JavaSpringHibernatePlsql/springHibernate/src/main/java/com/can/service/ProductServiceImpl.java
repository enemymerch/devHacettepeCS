package com.can.service;

import com.can.dao.ProductDao;
import com.can.model.Product;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDao productDao;

    public ArrayList<Product> getAll() {
        return (ArrayList<Product>) productDao.getProducts();
    }

    public String addProduct(String name) {
        String message;
        ArrayList<Product> products = (ArrayList<Product>) productDao.getProducts();
        boolean isProductNameUnique = true;
        for(Product product: products){
            if(product.getName().equals(name)){
                isProductNameUnique = false;
                break;
            }
        }
        if (isProductNameUnique){
            Product product = new Product();
            product.setName(name);
            productDao.addProduct(product);
            message = "Product added.";
        } else {
            message = "Product cannot be added.";
        }
        return message;
    }

    public String updateProduct(int id, String name) {
        String message = "Product is not updated.";
        Product product = productDao.getProductById(id);
        if(product.getId() == id) {
            product.setName(name);
            productDao.updateProduct(product);
            message = "Product updated";
        }
        return message;
    }

    public String deleteProduct(int id) {
        productDao.removeProduct(id);
        return "";
    }

    public boolean doesProductExitsWithName(String name) {
        ArrayList<Product> products = (ArrayList<Product>) productDao.getProducts();
        for(Product product:products){
            if(product.getName().equals(name)){
                return true;
            }
        }
        return false;
    }
}
