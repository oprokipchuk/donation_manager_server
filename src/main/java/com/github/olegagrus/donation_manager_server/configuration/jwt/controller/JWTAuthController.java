package com.github.olegagrus.donation_manager_server.configuration.jwt.controller;

import com.github.olegagrus.donation_manager_server.configuration.jwt.model.JWTAuthenticationRequest;
import com.github.olegagrus.donation_manager_server.configuration.jwt.model.JWTAuthenticationResponse;
import com.github.olegagrus.donation_manager_server.configuration.jwt.service.JWTAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/jwt")
public class JWTAuthController {

    private final JWTAuthService jwtAuthService;

    @Autowired
    public JWTAuthController(JWTAuthService jwtAuthService) {
        this.jwtAuthService = jwtAuthService;
    }

    @PostMapping("/auth")
    public ResponseEntity<JWTAuthenticationResponse> authenticate(
            @RequestBody JWTAuthenticationRequest request,
            HttpServletResponse response) {
        return ResponseEntity
                .ok()
                .body(jwtAuthService.authenticate(request, response));
    }

    @GetMapping
    public ResponseEntity<String> hello() {
        return ResponseEntity
                .ok()
                .body("hello");
    }

}
