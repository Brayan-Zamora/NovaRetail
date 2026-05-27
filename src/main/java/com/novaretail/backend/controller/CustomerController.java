package com.novaretail.backend.controller;

import com.novaretail.backend.dto.UserCreateDTO;
import com.novaretail.backend.dto.UserUpdateDTO;
import com.novaretail.backend.service.CustomerService;
import com.novaretail.backend.dto.CustomerCreateDTO;
import com.novaretail.backend.dto.CustomerResponseDTO;
import com.novaretail.backend.dto.CustomerUpdateDTO;
import com.novaretail.backend.dto.ChangePasswordDTO;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/customer")
@CrossOrigin(origins = "*")

public class CustomerController {
    
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    @GetMapping("/getCustomers")
    public ResponseEntity<List<CustomerResponseDTO>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomer());
    }

    @GetMapping("/getCustomer/{id}")
    public ResponseEntity<CustomerResponseDTO> getCustomerById(@PathVariable @NonNull Long id) {

        CustomerResponseDTO customer = customerService
                .returnCustomerById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado"));

        return ResponseEntity.ok(customer);
    }

     @PostMapping(path = "/createCustomer",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerResponseDTO> createCustomer(@RequestBody CustomerCreateDTO customerCreateDTO) {
        try {
            return ResponseEntity.ok(customerService.createCustomer(customerCreateDTO));
        }catch (Exception e) {
            return new ResponseEntity<CustomerResponseDTO>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/updateCustomer/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerResponseDTO> updateCustomer(@RequestBody CustomerUpdateDTO customerUpdateDTO, @PathVariable @NonNull Long id) {
        try {
            return ResponseEntity.ok(customerService.updateCustomer(id, customerUpdateDTO));
        }catch (Exception e) {
            return new ResponseEntity<CustomerResponseDTO>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @DeleteMapping("/deleteCustomer/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable @NonNull Long id) {
        try {
            customerService.deleteCustomer(id);
            return ResponseEntity.ok("Eliminado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
