package com.github.olegagrus.donation_manager_server.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StreamLabsTokenResponse {

    private String access_token;
    private String refresh_token;
    private String token_type;
    private int expires_in;

}
