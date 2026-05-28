package com.novaretail.backend.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.novaretail.backend.dto.ChangePasswordDTO;
import com.novaretail.backend.dto.UserUpdateDTO;
import com.novaretail.backend.entity.User;
import com.novaretail.backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    public User getUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("usuario no encontrado"));
    }

    public User updateUser(Long id, UserUpdateDTO dto){
        User user = getUserById(id);

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());

        return userRepository.save(user);
    }

    public void changePassword(Long id, ChangePasswordDTO dto){
        User user = getUserById(id);

        if(!passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword())){
            throw new RuntimeException("Contrasena acutal incorrecta");
        }

        if (passwordEncoder.matches(dto.getNewPassword(), user.getPassword())){
            throw new RuntimeException("La nueva contrasena no puede ser igual a la anterior");
        }

        String encoededPassword = passwordEncoder.encode(dto.getNewPassword());

        user.setPassword(encoededPassword);
            
        userRepository.save(user);
    }

    public void delete(Long id){
        userRepository.deleteById(id);
    }
}
