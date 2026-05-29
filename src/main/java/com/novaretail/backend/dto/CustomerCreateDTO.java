package com.novaretail.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerCreateDTO {

    @NotBlank
    private String name;
    
    @NotBlank
    @Email
    private String email;

    private String phone;
    
    private String address;

    @NotBlank
    private Long user_id;
}
