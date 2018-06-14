package com.can.service;

import com.can.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface ProductService {
    ArrayList<Product> getAll();
    String addProduct(String name);
    String updateProduct(int id, String name);
    String deleteProduct(int id);
    boolean doesProductExitsWithName(String name);
}
