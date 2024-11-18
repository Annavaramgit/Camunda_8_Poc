package com.aus.corsafe.service;

import com.aus.corsafe.config.ApplicationConfig;
import com.aus.corsafe.dto.MapperClass;
import com.aus.corsafe.dto.UserRegisterDto;
import com.aus.corsafe.entity.SecurityQuestion;
import com.aus.corsafe.entity.SecurityQuestionKey;
import com.aus.corsafe.entity.UserRegister;
import com.aus.corsafe.exceptions.BadCrediantialsCls;
import com.aus.corsafe.exceptions.UserNotFoundExceptionCls;
import com.aus.corsafe.repository.SecurityQuestionRepository;
import com.aus.corsafe.repository.UserRegisterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserRegisterServiceImpl implements UserRegisterService {
    @Autowired
    public MapperClass mapperClass;
    public PasswordEncoder encoder;
    public UserRegisterRepo userRegisterRepo;
    private SecurityQuestionRepository securityQuestionRepository;

    public UserRegisterServiceImpl(UserRegisterRepo userRegisterRepo, PasswordEncoder encoder, SecurityQuestionRepository securityQuestionRepository) {
        this.userRegisterRepo = userRegisterRepo;
        this.securityQuestionRepository = securityQuestionRepository;
        this.encoder = encoder;
    }

    @Override
    public UserRegisterDto register(UserRegisterDto userRegisterDto) {
        UserRegister userRegister = mapperClass.userRegisterDtoTOUserRegister(userRegisterDto);
        userRegister.setPassword(encoder.encode(userRegister.getPassword()));
        List<SecurityQuestionKey> list = userRegisterDto.getSecurityQuestionList();
        if (list != null) {
            List<SecurityQuestionKey> newList = new ArrayList<>();
            for (SecurityQuestionKey question : list) {
                question.setUserRegister(userRegister);
            newList.add(question);
            }
            userRegister.setSecurityQuestionList(newList);
        }
        return mapperClass.userRegisterTODto(userRegisterRepo.save(userRegister));

    }


    @Override
    public List<SecurityQuestion> getAllSecurityQuestion() {
        return securityQuestionRepository.findAll();
    }


    @Override
    public String findEmailByEmail(String email) {
        Optional<UserRegister> user = userRegisterRepo.findByEmail(email);
        if (user.isPresent()) {
            return user.get().getEmail();
        } else {
            throw new BadCrediantialsCls(ApplicationConfig.EMAIL_NOT_REGISTERED_MESSAGE + email);
        }
    }




}




