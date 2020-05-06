package com.github.olegagrus.donation_manager_server.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Donation {

    private long id;
    private String username;
    private String message;
    private double amount;
    private String currency;
    private long created_at;

}
