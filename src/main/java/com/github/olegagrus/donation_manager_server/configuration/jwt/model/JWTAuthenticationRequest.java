package com.github.olegagrus.donation_manager_server.configuration.jwt.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JWTAuthenticationRequest {

    private String username;
    private String password;

}
