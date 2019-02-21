package com.example.demo.mode1.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "social")
@Data
public class SocialProperties {
    private Map<String, String> providers;
}
