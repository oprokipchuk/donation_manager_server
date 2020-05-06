package com.github.olegagrus.donation_manager_server.function.donation.service;

import com.github.olegagrus.donation_manager_server.entity.Donation;
import com.github.olegagrus.donation_manager_server.entity.DonationResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface DonationService {

    DonationResponse getDonations() throws IOException, URISyntaxException;

}
