package com.kamkacz.springdemo.dao;

import com.kamkacz.springdemo.entity.Customer;

import java.util.List;

public interface CustomerDAO {

    public List<Customer> getCustomers();

    void saveCustomer(Customer theCustomer);
}
