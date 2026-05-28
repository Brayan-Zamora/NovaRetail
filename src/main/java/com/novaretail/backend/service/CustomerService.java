package com.novaretail.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.novaretail.backend.dto.CustomerCreateDTO;
import com.novaretail.backend.dto.CustomerResponseDTO;
import com.novaretail.backend.dto.CustomerUpdateDTO;
import com.novaretail.backend.entity.Customer;
import com.novaretail.backend.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    
    public CustomerResponseDTO getCustomerResponseDTO(Customer customer){
        CustomerResponseDTO dto = new CustomerResponseDTO();
        dto.setId(customer.getId());
                dto.setName(customer.getName());
                dto.setEmail(customer.getEmail());
                dto.setPhone(customer.getPhone());
                dto.setAddress(customer.getAddress());

                return dto;
    }

    public List<CustomerResponseDTO> getAllCustomer() {

        return customerRepository.findAll()
                .stream()
                .map(customer -> {
                    CustomerResponseDTO dto = getCustomerResponseDTO(customer);

                    return dto;
                })
                .toList();
    }

    public Optional<Customer> getCustomerById(@NonNull Long id){
        Optional<Customer> customer = customerRepository.findById(id);
        return customer;
    }

    public Optional<CustomerResponseDTO> returnCustomerById(@NonNull Long id) {
        return getCustomerById(id)
                .map(this::getCustomerResponseDTO);
    }
    

    public CustomerResponseDTO  createCustomer(CustomerCreateDTO dto){
            Customer customer = new Customer();

            customer.setName(dto.getName());
            customer.setAddress(dto.getAddress());
            customer.setPhone(dto.getPhone());
            customer.setEmail(dto.getEmail());

            customerRepository.save(customer);
             return getCustomerResponseDTO(customer);
    }

    public CustomerResponseDTO updateCustomer(@NonNull Long id, CustomerUpdateDTO dto){
        Customer customer = getCustomerById(id)
         .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));


        customer.setName(dto.getName());
        customer.setAddress(dto.getAddress());
        customer.setPhone(dto.getPhone());
        customer.setEmail(dto.getEmail());

        customerRepository.save(customer);
        return getCustomerResponseDTO(customer);
    }

    public void deleteCustomer(@NonNull Long id){
        customerRepository.deleteById(id);
    }
}
