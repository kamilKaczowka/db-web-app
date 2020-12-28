package com.kamkacz.springdemo.service;

import com.kamkacz.springdemo.entity.Customer;

import java.util.List;

public interface CustomerService {

    public List<Customer> getCustomers();

    void saveCustomer(Customer theCustomer);
}
