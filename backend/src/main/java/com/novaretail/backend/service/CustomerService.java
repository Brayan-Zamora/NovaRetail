package com.novaretail.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.novaretail.backend.dto.CustomerCreateDTO;
import com.novaretail.backend.dto.CustomerUpdateDTO;
import com.novaretail.backend.entity.Customer;
import com.novaretail.backend.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    
    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id){
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("cliente no encontrado"));
    }

    public Customer createCustomer(CustomerCreateDTO dto){
            Customer customer = new Customer();

            customer.setName(dto.getName());
            customer.setAddress(dto.getAddress());
            customer.setPhone(dto.getPhone());
            customer.setEmail(dto.getEmail());

            return customerRepository.save(customer);
    }

    public Customer updateCustomer(Long id, CustomerUpdateDTO dto){
        Customer customer = getCustomerById(id);

        customer.setName(dto.getName());
        customer.setAddress(dto.getAddress());
        customer.setPhone(dto.getPhone());
        customer.setEmail(dto.getEmail());

        return customerRepository.save(customer);
    }

    public void delete(Long id){
        customerRepository.deleteById(id);
    }
}
