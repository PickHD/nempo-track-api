package com.github.PickHD.nempo_track_api.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "server.authentication")
@Data
public class AppConfig {
    private String username;
    private String password;
}
