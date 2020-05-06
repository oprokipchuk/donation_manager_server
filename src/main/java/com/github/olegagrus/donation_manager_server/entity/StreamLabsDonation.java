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

    /*{"donation_id":128648459,
    "created_at":1586539380,
    "currency":"USD",
    "amount":"15.0000000000",
    "name":"donator2",
    "message":"Take my money",
    "email":"u6K5uMAlaf"}*/

    private long donation_id;
    private long created_at;
    private String currency;
    private double amount;
    private String name;
    private String message;
    private String email;

}
