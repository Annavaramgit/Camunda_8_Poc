package com.aus.corsafe.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aus.corsafe.service.bpmnservice.AuthService;

import reactor.core.publisher.Mono;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/get-token")
    public Mono<ResponseEntity<String>> getToken() {
        return authService.getAuthToken()
                .map(token -> ResponseEntity.ok(token))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }
}
