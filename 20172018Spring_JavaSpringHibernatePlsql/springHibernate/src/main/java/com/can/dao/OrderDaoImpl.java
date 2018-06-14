package com.can.dao;

import com.can.model.Orders;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao {

    @PersistenceContext
    EntityManager entityManager;


    @Transactional()
    public void addOrder(Orders orders) {
        entityManager.persist(orders);
    }

    @SuppressWarnings("unchecked")
    @Transactional()
    public void updateOrder(Orders orders) {
        entityManager.merge(orders);
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<Orders> getOrders() {
        return entityManager.createQuery("select x from Orders x").getResultList();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public Orders getOrderById(int id) {
        Query query = entityManager.createQuery("select x from Orders x where x.id = ?1");
        query.setParameter(1, id);
        List<Orders> orders= query.getResultList();
        if(orders.size()>0){
            return orders.get(0);
        } else{
            return null;
        }
    }

    @Transactional()
    public void removeOrder(int id) {
        Orders orders=  getOrderById(id);
        entityManager.remove(orders);
    }
}
