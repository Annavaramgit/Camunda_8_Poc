package com.aus.corsafe.controller;

import java.util.List;
import java.util.Map;

import com.aus.corsafe.dto.LoginResponseCls;
import com.aus.corsafe.dto.UserRegisterDto;
import com.aus.corsafe.entity.ResponseModel;
import com.aus.corsafe.entity.SecurityQuestion;
import com.aus.corsafe.exceptions.BadCrediantialsCls;
import com.aus.corsafe.response.CommonResponse;
import com.aus.corsafe.service.LoginService;
import com.aus.corsafe.service.UserRegisterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.aus.corsafe.config.OAuthAuthenticationSuccessHandler;
import com.aus.corsafe.dto.Login;

@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:3000/**", allowCredentials = "true")
public class UserRegiserController {

    // this is for jwt and refresh token
    @Autowired
    private CommonResponse<LoginResponseCls> commonResponse;

    private UserRegisterService userRegisterService;

    @Autowired
    private LoginService loginService;

    @Autowired
    public OAuthAuthenticationSuccessHandler oauthAuthenticationHandler;

    public UserRegiserController(UserRegisterService userRegisterService) {
        this.userRegisterService = userRegisterService;
    }

    //register
    @PostMapping("/register")
    public ResponseEntity<ResponseModel<Object>> register(@RequestBody UserRegisterDto userRegister) {

        log.info("user dto {}", userRegister);
        UserRegisterDto register = userRegisterService.register(userRegister);
        return new CommonResponse<>().prepareSuccessResponseObject(register, HttpStatus.CREATED);
    }

    //login
    @PostMapping("/login")
    public ResponseEntity<ResponseModel<LoginResponseCls>> login(@RequestBody Login login) {
        log.info("login is executing && email is:" + login.getEmail() + " password is: " + login.getPassword());
        try {
            LoginResponseCls res = loginService.tokenGenarationMethod(login);
            return commonResponse.prepareSuccessResponseObject(res, HttpStatus.OK);
        } catch (Exception e) {
            log.info("error:  " + e.toString());
            return commonResponse.prepareFailedResponse("Invalid !!");
        }

    }

    //	for google authentication hitting point
    @RequestMapping("/user")
    public void doGoogleAuthentication() {
        log.info("doGoogleAuthentication method !!");
    }

    //	google auth login jwt token generation
    @PostMapping("/googlelogin")
    public ResponseEntity<?> googleLogin(@RequestBody Map<String, String> payload) {

        String code = payload.get("code");
        log.info("Entering Google Login method code :{}", code);
        String accessToken = oauthAuthenticationHandler.fetchAccessTokenFromGoogle(code);
        log.info("Entering Google Login method got access token : {}", accessToken);
        String email = oauthAuthenticationHandler.fetchUserInfoFromGoogle(accessToken);
        log.info("Entering Google Login method got email: {}", email);

        LoginResponseCls res = loginService.tokenGenarationMethodUsingGoogle(email);
        if (res != null) {
            return commonResponse.prepareSuccessResponseObject(res, HttpStatus.OK);
        } else {
            return commonResponse.prepareFailedResponse("Invalid !!");
        }
    }

    @GetMapping("/getAllSecurityQuestion")
    public ResponseEntity<ResponseModel<Object>> getAllSecurityQuestion() {
        List<SecurityQuestion> questionList = userRegisterService.getAllSecurityQuestion();
        return new CommonResponse<>().prepareSuccessResponseObject(questionList, HttpStatus.OK);
    }

    //for test purpose
    @GetMapping("/test")
    public ResponseEntity<String> testMethod() {
        log.info("Test method is executing");
        return new ResponseEntity<>("test successfull", HttpStatus.OK);
    }

    //refresh token
    @PostMapping("/refreshToken/{token}")
    public ResponseEntity<ResponseModel<Object>> refreshTokenMethod(@PathVariable String token) {
        try {
            String accessToken = loginService.refreshTokenGenaration(token);
            return new CommonResponse<>().prepareSuccessResponseObject(accessToken, HttpStatus.OK);

        } catch (BadCrediantialsCls e) {
            return new CommonResponse<>().prepareFailedResponse(e.getMessage());
        } catch (Exception e) {
            return new CommonResponse<>().prepareFailedResponse("bad credentials or token");
        }
    }

    //find user by email
    @GetMapping("/findEmail/{email}")
    public ResponseEntity<ResponseModel<Object>> findEmailByEmail(@PathVariable String email) {
        try {
            String foundEmail = userRegisterService.findEmailByEmail(email);

            return new CommonResponse<>().prepareSuccessResponseObject(foundEmail, HttpStatus.OK);
        } catch (BadCrediantialsCls e) {
            log.info("Error is:" + e.toString());
            return new CommonResponse<>().prepareFailedResponse("Email not registered: " + email);
        }
    }
}

