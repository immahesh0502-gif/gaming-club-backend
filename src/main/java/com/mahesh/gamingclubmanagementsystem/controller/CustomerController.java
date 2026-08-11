package com.mahesh.gamingclubmanagementsystem.controller;

import com.mahesh.gamingclubmanagementsystem.entity.Customer;
import com.mahesh.gamingclubmanagementsystem.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.mahesh.gamingclubmanagementsystem.dto.CustomerDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public Customer saveCustomer(
            @Valid @RequestBody CustomerDTO customerDTO) {

        return customerService.saveCustomer(customerDTO);
    }
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }
    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }
    @GetMapping("/page")
    public Page<Customer> getCustomers(Pageable pageable) {
        return customerService.getCustomers(pageable);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {

        try {

            customerService.deleteCustomer(id);

            return ResponseEntity.ok("Customer Deleted Successfully");

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());

        }

    }
    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable Long id,
                                   @RequestBody Customer customer) {
        return customerService.updateCustomer(id, customer);
    }
    @GetMapping("/search")
    public List<Customer> searchCustomers(
            @RequestParam String name) {

        return customerService.searchCustomers(name);
    }
    @GetMapping("/search/jpql")
    public List<Customer> searchCustomerByNameJPQL(
            @RequestParam String name) {

        return customerService.searchCustomerByNameJPQL(name);
    }

    @GetMapping("/search/mobile")
    public List<Customer> searchCustomersByMobileNumber(
            @RequestParam String mobileNumber) {

        return customerService
                .searchCustomersByMobileNumber(mobileNumber);
    }
    @GetMapping("/search/contains")
    public List<Customer> searchCustomersContaining(
            @RequestParam String name) {

        return customerService.searchCustomersContaining(name);
    }
    @GetMapping("/search/jpql/like")
    public List<Customer> searchCustomerByNameLike(
            @RequestParam String name) {

        return customerService.searchCustomerByNameLike(name);
    }
    @GetMapping("/search/native")
    public List<Customer> searchCustomerByNameNative(
            @RequestParam String name) {

        return customerService.searchCustomerByNameNative(name);
    }
    @GetMapping("/search/native/like")
    public List<Customer> searchCustomerByNameLikeNative(
            @RequestParam String name) {

        return customerService.searchCustomerByNameLikeNative(name);
    }

    // Count Customers
    @GetMapping("/count")
    public Long countCustomers() {
        return customerService.countCustomers();
    }

    // Maximum Customer ID
    @GetMapping("/max-id")
    public Long getMaxCustomerId() {
        return customerService.getMaxCustomerId();
    }

    // Minimum Customer ID
    @GetMapping("/min-id")
    public Long getMinCustomerId() {
        return customerService.getMinCustomerId();
    }

    // Native SQL Count
    @GetMapping("/count/native")
    public Long countCustomersNative() {
        return customerService.countCustomersNative();
    }

}