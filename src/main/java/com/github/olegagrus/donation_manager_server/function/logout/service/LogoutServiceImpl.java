package com.github.olegagrus.donation_manager_server.function.logout.service;

import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class LogoutServiceImpl implements LogoutService {

    @Override
    public String logout(HttpServletResponse response) {
        deleteTokenCookie(response);
        return "successful logout";
    }

    private void deleteTokenCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("access-token", "");
        cookie.setMaxAge(0);
        cookie.setSecure(false);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        response.addCookie(cookie);
    }

}
