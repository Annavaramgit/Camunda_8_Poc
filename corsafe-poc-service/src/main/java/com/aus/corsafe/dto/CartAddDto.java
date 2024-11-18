package com.aus.corsafe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CartAddDto {

    private Integer userId;
    private Integer productId;
    private Integer quantity;
}
