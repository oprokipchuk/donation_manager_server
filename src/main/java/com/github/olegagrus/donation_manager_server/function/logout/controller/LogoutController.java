package com.github.olegagrus.donation_manager_server.function.logout.controller;

import com.github.olegagrus.donation_manager_server.function.logout.service.LogoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/logout")
public class LogoutController {

    private final LogoutService logoutService;

    @Autowired
    public LogoutController(LogoutService logoutService) {
        this.logoutService = logoutService;
    }

    @GetMapping
    public ResponseEntity<String> logout(HttpServletResponse response) {
        return ResponseEntity
                .ok()
                .body(logoutService.logout(response));
    }

}
