package com.novaretail.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.novaretail.backend.dto.ChangePasswordDTO;
import com.novaretail.backend.dto.UserCreateDTO;
import com.novaretail.backend.dto.UserResponseDTO;
import com.novaretail.backend.dto.UserUpdateDTO;
import com.novaretail.backend.entity.Role;
import com.novaretail.backend.entity.User;
import com.novaretail.backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDTO getUserResponseDTO(User user){
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
                dto.setName(user.getName());
                dto.setEmail(user.getEmail());
                dto.setRole(user.getRole().toString());

                return dto;
    }

    public List<UserResponseDTO> getAllUser(){
        return userRepository.findAll()
                .stream()
                .map(user -> {
                    UserResponseDTO dto = getUserResponseDTO(user);

                    return dto;
                })
                .toList();
    }

    public Optional<User> getUserById(@NonNull Long id){
        Optional<User> user = userRepository.findById(id);
        return user;
    }

    public Optional<UserResponseDTO> returnUserById(@NonNull Long id) {
        return getUserById(id)
                .map(this::getUserResponseDTO);
    }

    public UserResponseDTO createUser( UserCreateDTO dto){
       
        User user= new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole(Role.CUSTOMER);

        userRepository.save(user);
        return getUserResponseDTO(user);
    }

    public UserResponseDTO updateUser(@NonNull Long id, UserUpdateDTO dto){
        User user = getUserById(id)
        .orElseThrow(() -> new RuntimeException("User does not exist"));

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());

        userRepository.save(user);
        return getUserResponseDTO(user);
    }

    public void changePassword(@NonNull Long id, ChangePasswordDTO dto){
        User user = getUserById(id)
        .orElseThrow(() -> new RuntimeException("User does not exist"));;

        if(!passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword())){
            throw new RuntimeException("Wrong password");
        }

        if (passwordEncoder.matches(dto.getNewPassword(), user.getPassword())){
            throw new RuntimeException("The new password can't be the same as the past password");
        }

        String encoededPassword = passwordEncoder.encode(dto.getNewPassword());

        user.setPassword(encoededPassword);
            
        userRepository.save(user);
    }

    public void delete(@NonNull Long id){
        userRepository.deleteById(id);
    }
}
