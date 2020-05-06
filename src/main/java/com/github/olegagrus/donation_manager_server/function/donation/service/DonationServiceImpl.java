package com.github.olegagrus.donation_manager_server.function.donation.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.olegagrus.donation_manager_server.entity.Donation;
import com.github.olegagrus.donation_manager_server.entity.DonationResponse;
import com.github.olegagrus.donation_manager_server.function.auth.service.AuthService;
import com.github.olegagrus.donation_manager_server.function.donation.DonationKeys;
import com.github.olegagrus.donation_manager_server.function.donation.DonationProperties;
import com.github.olegagrus.donation_manager_server.function.user.service.UserService;
import com.github.olegagrus.donation_manager_server.function.user_tokens.repository.UserTokensRepository;
import com.github.olegagrus.donation_manager_server.model.User;
import com.github.olegagrus.donation_manager_server.model.UserTokens;
import com.github.olegagrus.donation_manager_server.pool.HttpClientPool;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class DonationServiceImpl implements DonationService {

    private Logger logger = LoggerFactory.getLogger(DonationServiceImpl.class);
    private ObjectMapper objectMapper = new ObjectMapper();
    private ModelMapper modelMapper;
    private final DonationProperties donationProperties;
    private final UserService userService;
    private final UserTokensRepository userTokensRepository;
    private final AuthService authService;

    @Autowired
    public DonationServiceImpl(ModelMapper modelMapper, DonationProperties donationProperties, UserService userService, UserTokensRepository userTokensRepository, AuthService authService) {
        this.modelMapper = modelMapper;
        this.donationProperties = donationProperties;
        this.userService = userService;
        this.userTokensRepository = userTokensRepository;
        this.authService = authService;
    }

    @Override
    public DonationResponse getDonations() throws IOException, URISyntaxException {

        User user = userService.loadUserFromAuthentication();
        List<UserTokens> userTokensList = userTokensRepository.findAllByUser(user);

        List<Donation> result = new ArrayList<>();
        List<Long> okIds = new ArrayList<>();

        for (int i = 0; i < userTokensList.size(); i++) {
            result.addAll(getDonationsFromServer(userTokensList.get(i), okIds));
        }

        result.sort((a, b) -> Long.compare(b.getCreated_at(), a.getCreated_at()));
        return new DonationResponse(result, okIds);
    }

    private List<Donation> getDonationsFromServer(UserTokens userTokens, List<Long> okIds) throws URISyntaxException, IOException {
        URIBuilder uriBuilder = new URIBuilder(userTokens.getDonationProvider().getDonationsUrl());
        uriBuilder.addParameter(DonationKeys.GET_DONATIONS_PARAMETER_ACCESS_TOKEN, userTokens.getAccessToken());

        HttpGet httpGet = new HttpGet(uriBuilder.build());
        httpGet.addHeader("withCredentials", "true");

        long responseTimeStart = System.currentTimeMillis();
        CloseableHttpResponse httpResponse = HttpClientPool.getClient().execute(httpGet);

        logResponse(httpResponse, responseTimeStart);

        if (httpResponse.getStatusLine().getStatusCode() != HttpServletResponse.SC_OK) {
            httpResponse.close();
            UserTokens newUserTokens =
                    authService.handleAccessExpired(userTokens.getRefreshToken(), userTokens.getDonationProvider());
            if (newUserTokens != null) return getDonationsFromServer(newUserTokens, okIds);
            return new ArrayList<>();
        }

        okIds.add(userTokens.getDonationProvider().getId());

        StringBuilder jsonBuilder = new StringBuilder();

        try(Scanner scanner = new Scanner(httpResponse.getEntity().getContent())) {
            while (scanner.hasNextLine()) {
                jsonBuilder.append(scanner.nextLine());
            }
        }

        String json = jsonBuilder.toString();

        logger.info(json);

        httpResponse.close();

        switch (userTokens.getDonationProvider().getName()) {
            case "StreamLabs":
                return getDonationListFromStreamLabsJson(json);
            default:
                return new ArrayList<>();
        }
    }

    private void logResponse(HttpResponse httpResponse, long responseTimeStart) {
        long timeTaken = System.currentTimeMillis() - responseTimeStart;
        StatusLine statusLine = httpResponse.getStatusLine();
        logger.info("Time: " + timeTaken + " ms " + statusLine.getReasonPhrase() + " " + statusLine.getStatusCode());
    }

    private List<Donation> getDonationListFromStreamLabsJson(String json) throws JsonProcessingException {
        return Arrays
                .stream(objectMapper.readValue(objectMapper.readTree(json).get("data").toString(), DonationKeys.PROVIDER_STREAMLABS_ARRAY))
                .map(src -> modelMapper.map(src, Donation.class))
                .collect(Collectors.toList());
    }

}
