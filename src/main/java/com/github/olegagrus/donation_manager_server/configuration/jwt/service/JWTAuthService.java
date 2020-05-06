package com.github.olegagrus.donation_manager_server.configuration.jwt.service;

import com.github.olegagrus.donation_manager_server.configuration.jwt.model.JWTAuthenticationRequest;
import com.github.olegagrus.donation_manager_server.configuration.jwt.model.JWTAuthenticationResponse;

import javax.servlet.http.HttpServletResponse;

public interface JWTAuthService {

    JWTAuthenticationResponse authenticate(JWTAuthenticationRequest request, HttpServletResponse response);

}
