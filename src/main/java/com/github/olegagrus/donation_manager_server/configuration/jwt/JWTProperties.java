package com.github.olegagrus.donation_manager_server.configuration.jwt;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@NoArgsConstructor
@Data
@ConfigurationProperties(prefix = "jwt")
public class JWTProperties {

    private String secret;

}
