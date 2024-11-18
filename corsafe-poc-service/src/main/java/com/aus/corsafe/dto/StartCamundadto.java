package com.aus.corsafe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class StartCamundadto {
    private Integer userId;
    private String firstName;
    private String lastName;
    private String email;
    private Long phoneNumber;
    private String abn;
    private String companyName;
    private String companyAddress;
    private String state;
    private String postalCode;
    private Integer orderId;
}
