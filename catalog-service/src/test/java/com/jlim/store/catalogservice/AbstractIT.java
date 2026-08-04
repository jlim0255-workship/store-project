package com.jlim.store.catalogservice;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// MEAT: the SpringBootTest.WebEnvironment.RANDOM_PORT will start the application on a random port, (test the whole
// flow)
// and the @LocalServerPort annotation will inject that port into the test class.
// This allows us to test the application in a real environment without hardcoding the port number.
public abstract class AbstractIT {
    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }
}
