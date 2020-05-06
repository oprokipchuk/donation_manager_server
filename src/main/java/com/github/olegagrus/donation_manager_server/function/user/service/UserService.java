package com.github.olegagrus.donation_manager_server.function.user.service;

import com.github.olegagrus.donation_manager_server.dto.UserDto;
import com.github.olegagrus.donation_manager_server.model.User;

public interface UserService {

    User loadUserFromAuthentication();

    UserDto getCurrentUser();

}
