package com.github.olegagrus.donation_manager_server.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StreamLabsDonation {

    private long donation_id;
    private long created_at;
    private String currency;
    private double amount;
    private String name;
    private String message;
    private String email;

}
