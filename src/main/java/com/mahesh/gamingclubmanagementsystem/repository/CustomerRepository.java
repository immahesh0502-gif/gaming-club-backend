package com.mahesh.gamingclubmanagementsystem.repository;

import com.mahesh.gamingclubmanagementsystem.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByName(String name);
    List<Customer> findByMobileNumber(String mobileNumber);
    List<Customer> findByNameContaining(String name);
    @Query("SELECT c FROM Customer c WHERE c.name = :name")
    List<Customer> findCustomerByNameJPQL(@Param("name") String name);
    @Query("SELECT c FROM Customer c WHERE c.name LIKE %:name%")
    List<Customer> findCustomerByNameLike(@Param("name") String name);
    @Query(
            value = "SELECT * FROM customer WHERE name = ?1",
            nativeQuery = true
    )
    List<Customer> findCustomerByNameNative(String name);
    @Query(
            value = "SELECT * FROM customer WHERE name LIKE %?1%",
            nativeQuery = true
    )
    List<Customer> findCustomerByNameLikeNative(String name);

    // Count Total Customers
    @Query("SELECT COUNT(c) FROM Customer c")
    Long countCustomers();

    // Maximum Customer ID
    @Query("SELECT MAX(c.id) FROM Customer c")
    Long getMaxCustomerId();

    // Minimum Customer ID
    @Query("SELECT MIN(c.id) FROM Customer c")
    Long getMinCustomerId();

    // Native SQL Count
    @Query(
            value = "SELECT COUNT(*) FROM customer",
            nativeQuery = true
    )
    Long countCustomersNative();
}