package com.mahesh.gamingclubmanagementsystem.services;

import com.mahesh.gamingclubmanagementsystem.entity.Customer;
import com.mahesh.gamingclubmanagementsystem.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.mahesh.gamingclubmanagementsystem.dto.CustomerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer saveCustomer(CustomerDTO customerDTO) {

        Customer customer = new Customer();

        customer.setName(customerDTO.getName());
        customer.setMobileNumber(customerDTO.getMobileNumber());

        return customerRepository.save(customer);
    }
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    public void deleteCustomer(Long id) {

        try {

            customerRepository.deleteById(id);

        } catch (Exception e) {

            throw new RuntimeException(
                    "Customer has previous game sessions. Delete those sessions first."
            );

        }

    }

    public Customer updateCustomer(Long id, Customer customer) {

        Customer existingCustomer =
                customerRepository.findById(id).orElse(null);

        if(existingCustomer != null) {

            existingCustomer.setName(customer.getName());
            existingCustomer.setMobileNumber(customer.getMobileNumber());

            return customerRepository.save(existingCustomer);
        }

        return null;
    }
    public Page<Customer> getCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }
    public List<Customer> searchCustomers(String name) {
        return customerRepository.findByName(name);
    }
    public List<Customer> searchCustomerByNameJPQL(String name) {
        return customerRepository.findCustomerByNameJPQL(name);
    }
    public List<Customer> searchCustomersByMobileNumber(String mobileNumber) {
        return customerRepository.findByMobileNumber(mobileNumber);
    }
    public List<Customer> searchCustomersContaining(String name) {
        return customerRepository.findByNameContaining(name);
    }
    public List<Customer> searchCustomerByNameLike(String name) {
        return customerRepository.findCustomerByNameLike(name);
    }
    public List<Customer> searchCustomerByNameNative(String name) {
        return customerRepository.findCustomerByNameNative(name);
    }
    public List<Customer> searchCustomerByNameLikeNative(String name) {
        return customerRepository.findCustomerByNameLikeNative(name);
    }

    // Count Customers
    public Long countCustomers() {
        return customerRepository.countCustomers();
    }

    // Maximum Customer ID
    public Long getMaxCustomerId() {
        return customerRepository.getMaxCustomerId();
    }

    // Minimum Customer ID
    public Long getMinCustomerId() {
        return customerRepository.getMinCustomerId();
    }

    // Native SQL Count
    public Long countCustomersNative() {
        return customerRepository.countCustomersNative();
    }

}