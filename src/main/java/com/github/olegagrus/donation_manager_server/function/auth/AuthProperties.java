package com.github.olegagrus.donation_manager_server.function.auth;

import lombok.*;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@NoArgsConstructor
@Data
@ConfigurationProperties(prefix = "auth")
public class AuthProperties {

    private String token_url;
    private String grant_type_code;
    private String grant_type_refresh_token;
    private String client_id;
    private String client_secret;
    private String redirect_uri;

}