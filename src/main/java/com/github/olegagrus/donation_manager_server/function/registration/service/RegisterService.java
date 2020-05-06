package com.github.olegagrus.donation_manager_server.function.registration.service;

import com.github.olegagrus.donation_manager_server.dto.UserDto;

public interface RegisterService {

    UserDto register(UserDto userDto);

}
