package com.github.olegagrus.donation_manager_server.configuration.jwt.service;

import com.github.olegagrus.donation_manager_server.configuration.jwt.model.JWTAuthenticationRequest;
import com.github.olegagrus.donation_manager_server.configuration.jwt.model.JWTAuthenticationResponse;
import com.github.olegagrus.donation_manager_server.configuration.jwt.util.JWTUtil;
import com.github.olegagrus.donation_manager_server.configuration.security.details_service.DbUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@Service
public class JWTAuthServiceImpl implements JWTAuthService {

    private final AuthenticationManager authenticationManager;
    private final DbUserDetailsService userDetailsService;
    private final JWTUtil jwtUtil;

    @Autowired
    public JWTAuthServiceImpl(AuthenticationManager authenticationManager, DbUserDetailsService userDetailsService, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }


    @Override
    public JWTAuthenticationResponse authenticate(JWTAuthenticationRequest request, HttpServletResponse response) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword(), new ArrayList<>())
            );
            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
            String jwt = jwtUtil.generateToken(userDetails);
            setTokenCookie(response, jwt);
            return new JWTAuthenticationResponse(jwt);
        }
        catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password", e);
        }
    }

    private void setTokenCookie(HttpServletResponse response, String jwt) {
        Cookie cookie = new Cookie("access-token", jwt);
        cookie.setMaxAge(1000 * 60 * 60 * 10);
        cookie.setSecure(false);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        response.addCookie(cookie);
    }

}
