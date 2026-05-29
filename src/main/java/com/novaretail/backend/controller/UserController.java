package com.novaretail.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.novaretail.backend.dto.UserCreateDTO;
import com.novaretail.backend.dto.UserResponseDTO;
import com.novaretail.backend.dto.UserUpdateDTO;
import com.novaretail.backend.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {
        private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/getUsers")
    public ResponseEntity<List<UserResponseDTO>> getAllCustomers() {
        return ResponseEntity.ok(userService.getAllUser());
    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity<UserResponseDTO> getCustomerById(@PathVariable @NonNull Long id) {

        UserResponseDTO user = userService
                .returnUserById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        return ResponseEntity.ok(user);
    }

     @PostMapping(path = "/createUser",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserCreateDTO userCreateDTO) {
        try {
            return ResponseEntity.ok(userService.createUser(userCreateDTO));
        }catch (Exception e) {
            return new ResponseEntity<UserResponseDTO>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/updateUser/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseDTO> updateUser(@RequestBody UserUpdateDTO userUpdate, @PathVariable @NonNull Long id) {
        try {
            return ResponseEntity.ok(userService.updateUser(id, userUpdate));
        }catch (Exception e) {
            return new ResponseEntity<UserResponseDTO>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable @NonNull Long id) {
        try {
            userService.delete(id);
            return ResponseEntity.ok("User deleted");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
