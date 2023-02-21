package com.ntg.orderserviceonlineshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderLineItemRequestDTO {
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;
}
