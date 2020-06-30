package com.repository.Impl;

import com.model.Customer;
import com.repository.CustomerRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
@Transactional
public class CustomerRepoImpl implements CustomerRepository {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<Customer> findAll() {
        TypedQuery<Customer> query = entityManager.createQuery("SELECT c FROM Customer c",Customer.class);
        return query.getResultList();
    }

    @Override
    public Customer findById(Long id) {
        TypedQuery<Customer> query = entityManager.createQuery("SELECT c FROM Customer c WHERE c.id=:id",Customer.class);
        query.setParameter("id",id);
        try {
            return query.getSingleResult();
        }catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void save(Customer model) {
        if (model.getId()!=null){
            entityManager.merge(model);
        }else {
            entityManager.persist(model);
        }
    }

    @Override
    public void remove(Long id) {
        Customer customer = findById(id);
        if (customer!=null){
            entityManager.remove(customer);
        }
    }
}
