package com.example.customerms.service.impl;

import com.example.customerms.model.Customer;
import com.example.customerms.repository.CustomerRepository;
import com.example.customerms.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Optional<Customer> updateCustomer(Long id, Customer customer) {
        return customerRepository.findById(id).map(existingCustomer -> {
            existingCustomer.setNombre(customer.getNombre());
            existingCustomer.setApellido(customer.getApellido());
            existingCustomer.setEmail(customer.getEmail());
            return customerRepository.save(existingCustomer);
        });
    }

    @Override
    public boolean deleteCustomer(Long id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return true;
        }
            return false;
    }
}
