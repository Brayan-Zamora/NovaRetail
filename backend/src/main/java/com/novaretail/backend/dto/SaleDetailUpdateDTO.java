package com.novaretail.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaleDetailUpdateDTO {
    private Long productId;
    private Integer quantity;
}
