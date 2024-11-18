package com.aus.corsafe.controller;

import com.aus.corsafe.config.ApplicationConfig;
import com.aus.corsafe.entity.ForgotPassword;
import com.aus.corsafe.exceptions.PasswordMismatchException;
import com.aus.corsafe.response.CommonResponse;
import com.aus.corsafe.service.ForgotPasswordService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ForgotPasswordController {

    @Autowired
    private ForgotPasswordService forgotPasswordService;


    //this api is save the forgot password
    @PostMapping("/password/forgotPassword")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPassword forgotPassword) {
        try {
            String newPassword = forgotPasswordService.updatePassword(forgotPassword);
            return new CommonResponse<>().prepareSuccessResponseObject(ApplicationConfig.PASSWORD_UPDATE_SUCCESS,  HttpStatus.CREATED);
        } catch (BadRequestException | PasswordMismatchException e) {
            return new CommonResponse<>().prepareErrorResponseObject(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }



}
