package com.github.olegagrus.donation_manager_server.function.user.service;

import com.github.olegagrus.donation_manager_server.dto.UserDto;
import com.github.olegagrus.donation_manager_server.model.User;
import com.github.olegagrus.donation_manager_server.function.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public User loadUserFromAuthentication() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("user not found"));
    }

    @Override
    public UserDto getCurrentUser() {
        return modelMapper.map(loadUserFromAuthentication(), UserDto.class);
    }

}
