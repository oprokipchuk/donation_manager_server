package com.github.olegagrus.donation_manager_server.function.donation.controller;

import com.github.olegagrus.donation_manager_server.entity.Donation;
import com.github.olegagrus.donation_manager_server.entity.DonationResponse;
import com.github.olegagrus.donation_manager_server.function.donation.service.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/donation")
@CrossOrigin("http://localhost:3000")
public class DonationController {

    private DonationService donationService;

    @Autowired
    public DonationController(DonationService donationService) {
        this.donationService = donationService;
    }

    @GetMapping
    public ResponseEntity<DonationResponse> getDonations() throws IOException, URISyntaxException {
        return ResponseEntity
                .ok()
                .body(donationService.getDonations());
    }

}
