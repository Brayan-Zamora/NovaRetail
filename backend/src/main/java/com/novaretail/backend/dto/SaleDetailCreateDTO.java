package com.novaretail.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaleDetailCreateDTO {
    private Long productId;
    private Integer quantity;
}