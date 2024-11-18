package com.aus.corsafe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.aus.corsafe.config.JwtService;
import com.aus.corsafe.config.MyUserDetailasService;
import com.aus.corsafe.dto.Login;
import com.aus.corsafe.dto.LoginResponseCls;
import com.aus.corsafe.entity.UserRegister;
import com.aus.corsafe.exceptions.BadCrediantialsCls;
import com.aus.corsafe.exceptions.UnAuthorizedExceptionCls;
import com.aus.corsafe.repository.UserRegisterRepo;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LoginService {

    @Autowired
    public UserRegisterRepo userRegisterRepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailasService myUserDetailasService;

    @Autowired
    private JwtService jwtService;

    //refresh token
    public String refreshTokenGenaration(String jwtToken) {

        String subject = jwtService.getSubjectFromToken(jwtToken);

        if (jwtService.isTokenValid(jwtToken)) {
            return jwtService.genarateRefreshToken(myUserDetailasService.loadUserByUsername(subject));
        } else {
            throw new BadCrediantialsCls("BadRequest!! ");
        }

    }

    //return jwt+refresh token after login success
    public LoginResponseCls tokenGenarationMethod(Login login) {
        log.info("tokenGenaration method in loginservice cls entered!!");
        String userName = login.getEmail();
        String jwtToken = "";
        UserRegister userRegister;
        try {
             userRegister = userRegisterRepo.findByEmail(userName).get();
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword()));

            if (authenticate.isAuthenticated()) {
                log.info("checking is authenticated or not!!");
                jwtToken = jwtService.genarateJwtToken(myUserDetailasService.loadUserByUsername(login.getEmail()));

            } else {
                log.info("fails while checking authntication");

                throw new UnAuthorizedExceptionCls("Authenuthentication Failed !! Invalid Credentials!!");
            }
        } catch (Exception e) {
            System.out.println("error is loginservice catch block::: " + e.toString());
            throw new UnAuthorizedExceptionCls("Authenuthentication Failed !!");
        }

        return LoginResponseCls
                .builder()
                .jwtToken(jwtToken)
                .refreshToken(refreshTokenGenaration(jwtToken))
                .userRegister(userRegister)
                .build();
    }




    //return jwt+refresh token after login success using google
    public LoginResponseCls tokenGenarationMethodUsingGoogle(String email) {
    	email=email.trim();
    	log.info("user email : {}",email);
        UserRegister user = userRegisterRepo.findByEmail(email).get();
       // log.info("User Details in tokenGenaration method in loginservice: {}",user);
        if(user!=null) {
            log.info("tokenGenaration method in loginservice cls entered!!");
            String jwtToken = jwtService.genarateJwtToken(myUserDetailasService.loadUserByUsername(email));
            return LoginResponseCls.builder()
                    .jwtToken(jwtToken)
                    .refreshToken(refreshTokenGenaration(jwtToken))
                    .userRegister(user)
                    .build();
        }else{
            return null;
        }
    }


}
