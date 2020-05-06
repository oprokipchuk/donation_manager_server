package com.github.olegagrus.donation_manager_server.function.donation_provider.service;

import com.github.olegagrus.donation_manager_server.dto.DonationProviderDto;

import java.util.List;

public interface DonationProviderService {

    List<DonationProviderDto> getProviders();

}
