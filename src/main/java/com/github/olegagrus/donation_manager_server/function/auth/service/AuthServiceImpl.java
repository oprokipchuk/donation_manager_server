package com.github.olegagrus.donation_manager_server.function.auth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.olegagrus.donation_manager_server.entity.StreamLabsTokenResponse;
import com.github.olegagrus.donation_manager_server.function.auth.AuthKeys;
import com.github.olegagrus.donation_manager_server.function.auth.AuthProperties;
import com.github.olegagrus.donation_manager_server.function.donation_provider.repository.DonationProviderRepository;
import com.github.olegagrus.donation_manager_server.function.user.service.UserService;
import com.github.olegagrus.donation_manager_server.function.user_tokens.repository.UserTokensRepository;
import com.github.olegagrus.donation_manager_server.model.DonationProvider;
import com.github.olegagrus.donation_manager_server.model.User;
import com.github.olegagrus.donation_manager_server.model.UserTokens;
import com.github.olegagrus.donation_manager_server.pool.HttpClientPool;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class AuthServiceImpl implements AuthService {

    private ObjectMapper objectMapper;
    private final AuthProperties authProperties;
    private final DonationProviderRepository donationProviderRepository;
    private final UserTokensRepository userTokensRepository;
    private final UserService userService;

    @Autowired
    public AuthServiceImpl(AuthProperties authProperties, DonationProviderRepository donationProviderRepository, UserTokensRepository userTokensRepository, UserService userService) {
        objectMapper = new ObjectMapper();
        this.authProperties = authProperties;
        this.donationProviderRepository = donationProviderRepository;
        this.userTokensRepository = userTokensRepository;
        this.userService = userService;
    }

    @Override
    public UserTokens handleCodeReceive(String code) throws IOException {
        DonationProvider donationProvider = donationProviderRepository.findByName("StreamLabs");
        StreamLabsTokenResponse tokenResponse = getTokensFromStreamLabsServer(
                donationProvider.getTokenUrl(),
                code,
                authProperties.getGrant_type_code(),
                AuthKeys.GET_TOKEN_PARAM_CODE_NAME);
        System.out.println(tokenResponse);
        return saveTokens(tokenResponse, donationProvider);
    }

    @Override
    public UserTokens handleAccessExpired(String refreshToken, DonationProvider donationProvider) {
        try {
            StreamLabsTokenResponse tokenResponse = getTokensFromStreamLabsServer(
                    donationProvider.getTokenUrl(),
                    refreshToken,
                    authProperties.getGrant_type_refresh_token(),
                    AuthKeys.GET_TOKEN_PARAM_REFRESH_TOKEN_NAME);
            return saveTokens(tokenResponse, donationProvider);
        }
        catch (Exception ignored) {
            return null;
        }
    }

    private UserTokens saveTokens(StreamLabsTokenResponse tokenResponse, DonationProvider donationProvider) {
        User user = userService.loadUserFromAuthentication();
        UserTokens userTokens = userTokensRepository.findByUserAndDonationProvider(user, donationProvider)
                .orElse(UserTokens.builder()
                        .user(user)
                        .donationProvider(donationProvider)
                        .build());

        userTokens.setAccessToken(tokenResponse.getAccess_token());
        userTokens.setRefreshToken(tokenResponse.getRefresh_token());
        return userTokensRepository.save(userTokens);
    }

    private StreamLabsTokenResponse getTokensFromStreamLabsServer(String url, String code, String grantType, String codeParam) throws IOException {

        HttpPost httpPost = new HttpPost(url);

        List<NameValuePair> params = new ArrayList<>(4);
        params.add(
                new BasicNameValuePair(AuthKeys.GET_TOKEN_PARAM_GRANT_TYPE_NAME, grantType));
        params.add(
                new BasicNameValuePair(AuthKeys.GET_TOKEN_PARAM_CLIENT_ID_NAME, authProperties.getClient_id()));
        params.add(
                new BasicNameValuePair(AuthKeys.GET_TOKEN_PARAM_CLIENT_SECRET_NAME, authProperties.getClient_secret()));
        params.add(
                new BasicNameValuePair(AuthKeys.GET_TOKEN_PARAM_REDIRECT_URI_NAME, authProperties.getRedirect_uri()));
        params.add(
                new BasicNameValuePair(codeParam, code));

        httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

        long sendRequestStart = System.currentTimeMillis();
        CloseableHttpResponse response = HttpClientPool.getClient().execute(httpPost);
        System.out.println("Request time: " + (System.currentTimeMillis() - sendRequestStart));

        HttpEntity responseEntity = response.getEntity();

        System.out.println(response.getStatusLine().getStatusCode());
        System.out.println(response.getStatusLine().getReasonPhrase());

        StringBuilder responseJson = new StringBuilder();

        if (responseEntity != null) {
            try (Scanner scanner = new Scanner(responseEntity.getContent())) {
                while (scanner.hasNextLine()) {
                    responseJson.append(scanner.nextLine());
                }
            }
        }

        String responseJsonString = responseJson.toString();
        System.out.println(responseJsonString);

        response.close();
        return objectMapper.readValue(responseJsonString, StreamLabsTokenResponse.class);
    }

}
