package com.github.olegagrus.donation_manager_server.function.auth.service;

import com.github.olegagrus.donation_manager_server.model.DonationProvider;
import com.github.olegagrus.donation_manager_server.model.UserTokens;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface AuthService {

    UserTokens handleCodeReceive(String code) throws IOException;
    UserTokens handleAccessExpired(String refreshToken, DonationProvider donationProvider) throws IOException;
}
