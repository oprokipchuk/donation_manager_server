package com.github.olegagrus.donation_manager_server.function.logout.service;

import javax.servlet.http.HttpServletResponse;

public interface LogoutService {

    String logout(HttpServletResponse response);

}
