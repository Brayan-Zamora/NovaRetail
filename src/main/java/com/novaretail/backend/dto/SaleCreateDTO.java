package com.novaretail.backend.dto;


import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaleCreateDTO {
    
    @NotNull
    @Positive
    private Long customerId;

    @NotNull
    @Positive
    private Long userId;

    @NotEmpty
    private List<SaleDetailCreateDTO> details;
}