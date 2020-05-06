package com.github.olegagrus.donation_manager_server.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DonationResponse {

    List<Donation> donations;
    List<Long> okProviders;

}
