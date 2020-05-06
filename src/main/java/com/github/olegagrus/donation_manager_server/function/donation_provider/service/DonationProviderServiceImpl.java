package com.github.olegagrus.donation_manager_server.function.donation_provider.service;

import com.github.olegagrus.donation_manager_server.dto.DonationProviderDto;
import com.github.olegagrus.donation_manager_server.function.donation_provider.repository.DonationProviderRepository;
import com.github.olegagrus.donation_manager_server.model.DonationProvider;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DonationProviderServiceImpl implements DonationProviderService {

    private final DonationProviderRepository donationProviderRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public DonationProviderServiceImpl(DonationProviderRepository donationProviderRepository, ModelMapper modelMapper) {
        this.donationProviderRepository = donationProviderRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<DonationProviderDto> getProviders() {
        return donationProviderRepository.findAll().stream()
            .map((provider) -> modelMapper.map(provider, DonationProviderDto.class))
            .collect(Collectors.toList());
    }
}
