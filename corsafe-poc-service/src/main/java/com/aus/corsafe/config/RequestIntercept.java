package com.aus.corsafe.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;

import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import java.util.Map;
@Service
@Slf4j
public class RequestIntercept implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final String allowedEmail = "satya123.eidiko@gmail.com";
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("entered");
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate =
                new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // Extract email from the OAuth2User's attributes
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = (String) attributes.get("email");

        // Validate email
        if (!email.equals(allowedEmail)) {
            log.info("error");
            throw new OAuth2AuthenticationException("Unauthorized email: " + email);
        }

        // Set authentication in the context if email matches
        Authentication authentication =
                new OAuth2AuthenticationToken(oAuth2User, oAuth2User.getAuthorities(), userRequest.getClientRegistration().getRegistrationId());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return oAuth2User;
    }
}
