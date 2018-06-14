package com.can.service;

import com.can.model.Product;
import com.can.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    public ArrayList<Product> getAll() {
        return productRepository.getProducts();
    }

    public String addProduct(String name) {
        String message;
        ArrayList<Product> products = productRepository.getProducts();
        boolean isProductNameUnique = true;
        for(Product product: products){
            if(product.getName().equals(name)){
                isProductNameUnique = false;
                break;
            }
        }
        if (isProductNameUnique){
            productRepository.add(new Product(name));
            message = "Product added.";
        } else {
            message = "Product cannot be added.";
        }
        return message;
    }

    public String updateProduct(int id, String name) {
        String message = "Product is not updated.";
        ArrayList<Product> products = productRepository.getProducts();
        for(int i = 0 ; i < products.size() ; i++){
            Product product = products.get(i);
            if(product.getId() == id){
                product.setName(name);
                products.set(i, product);
                productRepository.setProducts(products);
                message = "Product updated";
                break;
            }
        }
        return message;
    }

    public String deleteProduct(int id) {
        String message = "Product with id "+ id +  " is not found.";
        ArrayList<Product> products = productRepository.getProducts();
        for(int i = 0 ; i < products.size() ; i++){
            Product product = products.get(i);
            if(product.getId() == id){
                products.remove(i);
                productRepository.setProducts(products);
                message = "Product deleted";
                break;
            }
        }
        return message;
    }

    public boolean doesProductExitsWithName(String name) {
        ArrayList<Product> products = productRepository.getProducts();
        for(Product product:products){
            if(product.getName().equals(name)){
                return true;
            }
        }
        return false;
    }
}
