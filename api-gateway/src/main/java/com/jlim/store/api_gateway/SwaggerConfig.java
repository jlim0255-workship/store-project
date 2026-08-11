package com.jlim.store.api_gateway;

import static org.springdoc.core.utils.Constants.DEFAULT_API_DOCS_URL;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springdoc.core.properties.AbstractSwaggerUiConfigProperties;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

@Configuration
class SwaggerConfig {
    private final RouteDefinitionLocator locator;
    private final SwaggerUiConfigProperties swaggerUiConfigProperties;

    public SwaggerConfig(RouteDefinitionLocator locator, SwaggerUiConfigProperties swaggerUiConfigProperties) {
        this.locator = locator;
        this.swaggerUiConfigProperties = swaggerUiConfigProperties;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void init() {
        List<RouteDefinition> definitions =
                locator.getRouteDefinitions().collectList().block();
        System.out.println("Routes found: " + definitions.size());
        Set<AbstractSwaggerUiConfigProperties.SwaggerUrl> urls = new HashSet<>();
        definitions.stream()
                .filter(routeDefinition -> routeDefinition.getId().matches(".*-service"))
                .forEach(routeDefinition -> {
                    String name = routeDefinition.getId().replaceAll("-service", "");
                    AbstractSwaggerUiConfigProperties.SwaggerUrl swaggerUrl =
                            new AbstractSwaggerUiConfigProperties.SwaggerUrl(
                                    name, DEFAULT_API_DOCS_URL + "/" + name, null);
                    urls.add(swaggerUrl);
                });
        swaggerUiConfigProperties.setUrls(urls);
    }
}
