package com.aus.corsafe.dto;


import com.aus.corsafe.entity.SecurityQuestionKey;
import com.aus.corsafe.entity.UserRegister;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class MapperClass {

    @Autowired
    public ModelMapper modelMapper;

    public UserRegisterDto userRegisterTODto(UserRegister userRegister)
    {
        return   modelMapper.map(userRegister,UserRegisterDto.class);
    }


    public UserRegister userRegisterDtoTOUserRegister(UserRegisterDto userRegisterDto)
    {
        return   modelMapper.map(userRegisterDto,UserRegister.class);
    }
   /* public UserRegister userRegisterDtoTOUserRegister(UserRegisterDto userRegisterDto) {
        UserRegister userRegister = new UserRegister();
        // Set other fields
        userRegister.setFirstName(userRegisterDto.getFirstName());
        userRegister.setLastName(userRegisterDto.getLastName());
        userRegister.setEmail(userRegisterDto.getEmail());
        userRegister.setPhoneNumber(userRegisterDto.getPhoneNumber());
        userRegister.setPassword(userRegisterDto.getPassword());
        userRegister.setAbn(userRegisterDto.getAbn());
        userRegister.setCompanyName(userRegisterDto.getCompanyName());
        userRegister.setCompanyAddress(userRegisterDto.getCompanyAddress());
        userRegister.setState(userRegisterDto.getState());
        userRegister.setPostalCode(userRegisterDto.getPostalCode());

        // Handle SecurityQuestionKey list
        List<SecurityQuestionKey> securityQuestions = userRegisterDto.getSecurityQuestionList();
        if (securityQuestions != null) {
            for (SecurityQuestionKey question : securityQuestions) {
                question.setUserRegister(userRegister); // Set the userRegister reference
            }
            userRegister.setSecurityQuestionList(securityQuestions);
        }

        return userRegister;
    }*/


}
