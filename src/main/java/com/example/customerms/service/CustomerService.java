package com.example.customerms.service;

import com.example.customerms.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    Customer saveCustomer(Customer customer);
    List<Customer> getAllCustomers();
    Optional<Customer> getCustomerById(Long id);
    Optional<Customer> updateCustomer(Long id, Customer customer);
    boolean deleteCustomer(Long id);
}