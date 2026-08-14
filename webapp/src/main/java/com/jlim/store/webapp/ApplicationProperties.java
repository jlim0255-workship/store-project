package com.jlim.store.webapp;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "store")
public record ApplicationProperties(String apiGatewayUrl) {}
