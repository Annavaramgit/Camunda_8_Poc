package com.aus.corsafe.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	private final RestTemplate restTemplate = new RestTemplate();

	private final String CLIENT_ID = "";
	private final String CLIENT_SECRET = "";
	private final String REDIRECT_URI = "http://localhost:3000/auth/callback";

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

	}

	public String fetchAccessTokenFromGoogle(String code) {
		String tokenUrl = "https://oauth2.googleapis.com/token";
		log.info("google varification code : {}", code);
		// Prepare the request body
		Map<String, String> requestBody = new HashMap<>();
		requestBody.put("code", code);
		requestBody.put("client_id", CLIENT_ID);
		requestBody.put("client_secret", CLIENT_SECRET);
		requestBody.put("redirect_uri", REDIRECT_URI);
		requestBody.put("grant_type", "authorization_code");

		// Exchange the code for an access token
		Map<String, Object> response = restTemplate.postForObject(tokenUrl, requestBody, Map.class);

		if (response != null && response.containsKey("access_token")) {
			return (String) response.get("access_token");
		}
		return null;
	}

	public String fetchUserInfoFromGoogle(String accessToken) {
		String userInfoUrl = "https://www.googleapis.com/oauth2/v3/userinfo";
		log.info("fetchUserInfoFromGoogle method access token:{}", accessToken);
		// Create headers and add the Authorization Bearer token
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		log.info("fetchUserInfoFromGoogle method access token with bearer : {}", accessToken);
		// Create an HttpEntity with headers
		HttpEntity<String> entity = new HttpEntity<>(headers);
		log.info("fetchUserInfoFromGoogle method entity : {}", entity);
		try {
			ResponseEntity<Map> response = restTemplate.exchange(userInfoUrl, HttpMethod.GET, entity, Map.class);
			log.info("fetchUserInfoFromGoogle method user google response : {}", response);
			return (String) response.getBody().get("email");
		} catch (HttpClientErrorException e) {
			log.error("Error fetching user info: {}", e.getResponseBodyAsString());
			throw new BadCredentialsException("Failed to fetch user info", e);
		}
	}

}
