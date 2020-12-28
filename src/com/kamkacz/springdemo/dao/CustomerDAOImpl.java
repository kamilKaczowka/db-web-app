package com.kamkacz.springdemo.dao;

import com.kamkacz.springdemo.entity.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

    // need to inject the session factory
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Customer> getCustomers() {

        // get the current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        // create a query sort by last name
        Query<Customer> theQuery =
                currentSession.createQuery("FROM Customer ORDER BY lastName",
                        Customer.class);

        // execute query and get result list
        List<Customer> customers = theQuery.getResultList();

        // return the results
        return customers;
    }

    @Override
    public void saveCustomer(Customer theCustomer) {

        // get current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        // save/update the customer
        currentSession.saveOrUpdate(theCustomer);
    }

    @Override
    public Customer getCustomer(int theId) {

        // get the current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        // retrieve/read from db using the primary key
        Customer theCustomer = currentSession.get(Customer.class, theId);

        return theCustomer;
    }

    @Override
    public void deleteCustomer(int theId) {

        // get the current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        // delete the customer
        Query theQuery =
                currentSession.createQuery("DELETE FROM Customer WHERE id=:customerId");
        theQuery.setParameter("customerId", theId);

        theQuery.executeUpdate();
    }

    @Override
    public List<Customer> searchCustomers(String theSearchName) {

        // get the current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        Query theQuery = null;

        // only search by name if theSearchName is not empty

        if(theSearchName != null && theSearchName.trim().length() > 0) {
            // search for firstName or lastName
            theQuery = currentSession.createQuery("FROM Customer WHERE " +
                    "lower(firstName) LIKE :theName or lower(lastName) LIKE  :theName", Customer.class);
            theQuery.setParameter("theName","%" + theSearchName.toLowerCase() + "%");
        }else {
            //theSearchName is empty, so just get all customers
            theQuery = currentSession.createQuery("FROM Customer", Customer.class);
        }

        // execute query and get result list
        List<Customer> customers = theQuery.getResultList();

        // return the results
        return customers;
    }
}
