package com.can.dao;

import com.can.model.Customer;
import com.can.model.Orders;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class CustomerDaoImpl implements CustomerDao{

    @PersistenceContext
    EntityManager entityManager;

    @Transactional()
    public void addCustomer(Customer customer) {
        entityManager.persist(customer);
    }

    @Transactional()
    public void updateCustomer(Customer customer) {
        entityManager.merge(customer);
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<Customer> getCustomers() {
        return entityManager.createQuery("select x from Customer x").getResultList();
    }


    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public Customer getCustomerById(int id) {
        Query query = entityManager.createQuery("select x from Customer x where x.id = ?1");
        query.setParameter(1, id);
        List<Customer> customers= query.getResultList();
        if(customers.size()>0){
            return customers.get(0);
        } else{
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    @Transactional()
    public void removeCustomer(int id) {
        Customer customer = getCustomerById(id);
        entityManager.remove(customer);
    }
}
