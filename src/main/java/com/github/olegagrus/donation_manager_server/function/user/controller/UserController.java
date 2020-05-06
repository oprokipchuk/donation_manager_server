package com.github.olegagrus.donation_manager_server.function.user.controller;

import com.github.olegagrus.donation_manager_server.dto.UserDto;
import com.github.olegagrus.donation_manager_server.function.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<UserDto> getUser() {
        return ResponseEntity
                .ok()
                .body(userService.getCurrentUser());
    }

}
