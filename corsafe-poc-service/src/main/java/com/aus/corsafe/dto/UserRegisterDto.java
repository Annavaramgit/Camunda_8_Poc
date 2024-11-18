package com.aus.corsafe.dto;


import java.util.List;

import com.aus.corsafe.entity.SecurityQuestionKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRegisterDto {

    private String firstName;
    private String lastName;
    private String email;
    private Long phoneNumber;
    private String password;
    private String abn;
    private String companyName;
    private String companyAddress;
    private String state;
    private Long postalCode;

    private List<SecurityQuestionKey> securityQuestionList;
}
