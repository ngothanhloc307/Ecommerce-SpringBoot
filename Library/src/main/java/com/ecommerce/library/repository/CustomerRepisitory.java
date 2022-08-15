package com.ecommerce.library.repository;

import com.ecommerce.library.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepisitory extends JpaRepository<Customer, Long> {
    public Customer findByUsername(String username);
}
