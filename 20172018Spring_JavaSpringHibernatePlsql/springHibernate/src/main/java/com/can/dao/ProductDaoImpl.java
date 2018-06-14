package com.can.dao;

import com.can.model.Product;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ProductDaoImpl implements ProductDao {
    @PersistenceContext
    EntityManager entityManager;


    @Transactional()
    public void addProduct(Product product) {
        entityManager.persist(product);
    }


    @Transactional()
    public void updateProduct(Product product) {
    entityManager.merge(product);
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<Product> getProducts() {
        return entityManager.createQuery("select x from Product x").getResultList();
    }

    public Product getProductById(int id) {
        Query query = entityManager.createQuery("select x from Product x where x.id = ?1");
        query.setParameter(1, id);
        List<Product> products = query.getResultList();
        if(products.size()>0){
            return products.get(0);
        } else{
            return null;
        }

    }

    @Transactional()
    public void removeProduct(int id) {
        Product product =  getProductById(id);
        entityManager.remove(product);
    }
}
