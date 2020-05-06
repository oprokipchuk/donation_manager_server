package com.github.olegagrus.donation_manager_server.dto;

import lombok.*;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"name", "authUrl"})
public class DonationProviderDto {

    private long id;

    private String name;

    private String authUrl;

}
