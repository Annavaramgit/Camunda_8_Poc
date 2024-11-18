package com.aus.corsafe.service;

import com.aus.corsafe.dto.UserRegisterDto;
import com.aus.corsafe.entity.SecurityQuestion;

import java.util.List;
import java.util.Optional;

public interface UserRegisterService {

    public UserRegisterDto register(UserRegisterDto userRegisterDto);
    public List<SecurityQuestion> getAllSecurityQuestion();

    String findEmailByEmail(String email); 
}
