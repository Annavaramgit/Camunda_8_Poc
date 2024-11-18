package com.aus.corsafe.service;

import com.aus.corsafe.config.ApplicationConfig;
import com.aus.corsafe.entity.ForgotPassword;
import com.aus.corsafe.entity.UserRegister;
import com.aus.corsafe.exceptions.PasswordMismatchException;
import com.aus.corsafe.repository.UserRegisterRepo;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ForgotPasswordService {

    @Autowired
    private UserRegisterRepo userRegisterRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String updatePassword(ForgotPassword forgotPassword) throws BadRequestException {
        if (!forgotPassword.getNewPassword().equals(forgotPassword.getConfirmPassword())) {
            throw new PasswordMismatchException(ApplicationConfig.PASSWORD_MISMATCH_MESSAGE);
        }

        Optional<UserRegister> userOptional = userRegisterRepo.findByEmail(forgotPassword.getEmail());
        if (userOptional.isPresent()) {
            UserRegister userRegister = userOptional.get();

            String encodePassword = passwordEncoder.encode(forgotPassword.getNewPassword());

            userRegister.setPassword(encodePassword);
            userRegisterRepo.save(userRegister);
            return forgotPassword.getNewPassword();
        } else {
            throw new BadRequestException(ApplicationConfig.USER_NOT_FOUND_MESSAGE);
        }
    }

}
