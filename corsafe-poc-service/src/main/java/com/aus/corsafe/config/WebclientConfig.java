package com.aus.corsafe.config;

import com.aus.corsafe.model.AccessTokenModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.time.Instant;
import java.util.Date;

@Configuration
@Slf4j
public class WebclientConfig {

    public static final String BASE_URL_CAMUNDA_CONNECTORS = "https://dsm-1.connectors.camunda.io/6feae57c-2f49-4861-a383-7c43f82db777/inbound/audit-request";

    @Value("${zeebe.client.cloud.clientId}")
    String clientId;

    @Value("${zeebe.client.cloud.clientSecret}")
    String clientSecret;

    private static String token;
    private static Date tokenExpireAt;

    /**
     * this is for camunda token generation webclient
     */

    @Bean
    public WebClient webClientTokenGeneration() {
        return WebClient.builder()
                .baseUrl(ApplicationConfig.BASE_URL_CAMUNDA_TOKEN)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    /**
     * this is for calling camunda webclient
     */

    @Bean
    @DependsOn("webClientTokenGeneration")
    public WebClient webClient() {

        return WebClient.builder()
                .baseUrl(ApplicationConfig.BASE_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .filter(ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
                    log.info("Request: {} {}", clientRequest.method(), clientRequest.url());
                    clientRequest.headers().forEach((name, values) ->
                            values.forEach(value -> log.info("{}: {}", name, value))
                    );
                    return Mono.just(clientRequest);
                }))
                .filter(addAutharizationFilter())
                .build();
    }

    // WebClient for connectors API (no authorization needed)
    @Bean
    public WebClient webClientConnectors() {
        return WebClient.builder()
                .baseUrl(BASE_URL_CAMUNDA_CONNECTORS)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }



    /**
     * used for to add authorization header with token
     */
    private ExchangeFilterFunction addAutharizationFilter() {

        return (request, next) -> {
            String token = getToken();
            return next.exchange(
                    ClientRequest.from(request)
                            .header(HttpHeaders.AUTHORIZATION, token)
                            .build()
            );
        };
    }

    private String getToken() {
        if (token == null || isExpired(token)) {
            AccessTokenModel accessTokenModel = generateToken();
            token = accessTokenModel.getAccess_token();
            tokenExpireAt = Date.from(Instant.now().plusSeconds(accessTokenModel.getExpires_in()));
        }
        return "Bearer " + token;
    }

    private boolean isExpired(String token) {

        return tokenExpireAt.before(new Date());
    }

    /**
     * this  call camunda authentication token genarataion api
     */
    public AccessTokenModel generateToken() {

        String body = "grant_type=client_credentials" +
                "&audience=tasklist.camunda.io" +
                "&client_id=" + clientId +
                "&client_secret=" + clientSecret +
                "&scope=tasklist.read";

        AccessTokenModel token = webClientTokenGeneration()
                .post()
                .uri("/oauth/token")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(AccessTokenModel.class)
                .block();
        return token;
    }

}