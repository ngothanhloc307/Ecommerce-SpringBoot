package com.ecommerce.library.service.imp;

import com.ecommerce.library.dto.CustomerDto;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.repository.CustomerRepisitory;
import com.ecommerce.library.repository.RoleRepository;
import com.ecommerce.library.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.lang.reflect.Array;
import java.util.Arrays;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private CustomerRepisitory customerRepisitory;

    @Override
    public CustomerDto save(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setUsername(customerDto.getUsername());
        customer.setPassword(customerDto.getPassword());
        customer.setRoles(Arrays.asList(roleRepository.findByName("CUSTOMER")));
        Customer customerSave = customerRepisitory.save(customer);
        return mapperDto(customerSave);
    }

    @Override
    public Customer findByUsername(String username) {
        return customerRepisitory.findByUsername(username);
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        Customer saveCustomer = customerRepisitory.findByUsername(customer.getUsername());
        saveCustomer.setPhoneNumber(customer.getPhoneNumber());
        saveCustomer.setAddress(customer.getAddress());
        saveCustomer.setCity(customer.getCity());
        saveCustomer.setCountry(customer.getCountry());
        return customerRepisitory.save(saveCustomer);
    }

    private CustomerDto mapperDto(Customer customer){
        CustomerDto customerDto = new CustomerDto();
        customerDto.setFirstName(customer.getFirstName());
        customerDto.setLastName(customerDto.getLastName());
        customerDto.setPassword(customerDto.getPassword());
        customerDto.setUsername(customer.getUsername());
        return customerDto;
    }
}
