package com.github.olegagrus.donation_manager_server.function.auth.controller;

import com.github.olegagrus.donation_manager_server.function.auth.AuthKeys;
import com.github.olegagrus.donation_manager_server.function.auth.service.AuthService;
import com.github.olegagrus.donation_manager_server.function.auth.service.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping
    public ResponseEntity<String> receiveCode(@RequestParam String code) throws IOException {
        authService.handleCodeReceive(code);
        return ResponseEntity
                .ok()
                .body(AuthKeys.CLOSE_WINDOW_TEMPLATE_STRING);
    }


}
