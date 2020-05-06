package com.github.olegagrus.donation_manager_server.function.donation_provider.controller;

import com.github.olegagrus.donation_manager_server.dto.DonationProviderDto;
import com.github.olegagrus.donation_manager_server.function.donation_provider.service.DonationProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/providers")
public class DonationProviderController {

    private final DonationProviderService donationProviderService;

    @Autowired
    public DonationProviderController(DonationProviderService donationProviderService) {
        this.donationProviderService = donationProviderService;
    }

    @GetMapping
    public ResponseEntity<List<DonationProviderDto>> getProviders() {
        return ResponseEntity
                .ok()
                .body(donationProviderService.getProviders());
    }

}
