package com.aus.corsafe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDto {

    private Integer userId;
    private Long cartId;
    private String adress;
    private String city;
    private String postalcode;
    private String area;

}
