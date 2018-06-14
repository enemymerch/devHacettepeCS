package com.can.dao;

import com.can.model.Product;

import java.util.List;

public interface ProductDao {
    public void addProduct(Product product);
    public void updateProduct(Product product);
    public List<Product> getProducts();
    public Product getProductById(int id);
    public void removeProduct(int id);
}
