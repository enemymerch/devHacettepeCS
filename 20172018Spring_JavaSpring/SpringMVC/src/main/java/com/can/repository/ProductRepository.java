package com.can.repository;


        import com.can.model.Product;
        import org.springframework.stereotype.Repository;

        import java.util.ArrayList;

@Repository
public interface ProductRepository{
    void add(Product product);
    void update(int id, String name);
    void delete(int id);
    ArrayList<Product> getProducts();
    void setProducts(ArrayList<Product> products);
    Product getProductByName(String name);
    Product getProductByID(int id);
}
