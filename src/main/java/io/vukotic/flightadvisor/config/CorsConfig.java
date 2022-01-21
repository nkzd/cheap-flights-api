package io.vukotic.flightadvisor.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "cors")
@Data
class CorsConfig {
    private List<String> allowedMethods;
    private List<String> allowedOrigins;
}
