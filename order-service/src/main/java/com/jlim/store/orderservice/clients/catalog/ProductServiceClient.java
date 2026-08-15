package com.jlim.store.orderservice.clients.catalog;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ProductServiceClient {
    private static final Logger log = LoggerFactory.getLogger(ProductServiceClient.class);

    private final RestClient restClient;

    ProductServiceClient(RestClient restClient) {
        this.restClient = restClient;
    }

    // MEAT:
    // DO NOT APPLY fallback inside circuit breaker
    // the priority is below
    // Retry ( CircuitBreaker ( RateLimiter ( TimeLimiter ( Bulkhead ( Function ) ) ) ) ) -> this is default but can be
    // modified
    // source: https://resilience4j.readme.io/docs/getting-started-3#aspect-order
    // if we apply fallback inside circuitbreaker, it will return the fallback, then no more retry, which is not we want
    @CircuitBreaker(name = "catalog-service")
    @Retry(name = "catalog-service", fallbackMethod = "getProductByCodeFallback")
    public Optional<Product> getProductByCode(String code) {
        // MEAT
        // default retry is 3 times
        // if we put try catch, we assume we will get a successful response,
        // but if we get a timeout, we will not retry, so we will get an exception.
        // So we need to let the exception propagate to the retry mechanism.
        log.info("Fetching product for code: {}", code);

        var product =
                restClient.get().uri("/api/products/{code}", code).retrieve().body(Product.class);

        return Optional.ofNullable(product);
    }

    // if we cannot get after all retries, fallback reject and return empty
    // do not assume, always check with the business team what is the default fallback if order cannot fulfil
    Optional<Product> getProductByCodeFallback(String code, Throwable t) {
        System.out.println(
                "ProuctServiceClient.getProductByCodeFallback: code: " + code + ", error: " + t.getMessage());
        return Optional.empty();
    }
}
