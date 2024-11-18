package com.aus.corsafe.service.bpmnservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class AuthService {
	private final WebClient webClient;
//    private final WebClient.Builder webClient;
    @Value("${zeebe.client.cloud.clientId}")
    private String clientId;

    @Value("${zeebe.client.cloud.clientSecret}")
    private String clientSecret;

    public AuthService(WebClient webClient) {
        this.webClient = webClient;
    }

    @Value("${webclient.base-url}")
    private String baseUrl;

    public Mono<String> getAuthToken() {
        String body = "grant_type=client_credentials" +
                "&audience=tasklist.camunda.io" +
                "&client_id=" + clientId +
                "&client_secret=" + clientSecret +
                "&scope=tasklist.read";

        return webClient
                .post()
                .uri("/oauth/token")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class);
    }

}
