package com.jlim.store.catalogservice.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// MEAT: specifying this will do testing using in-memory database (h2) need to specify dependency before using
// but we carry out the param here, spin up a postgres instance in a tc (reusable test container)
@DataJpaTest(
        properties = {
                "spring.test.database.replace=none",
                "spring.datasource.url=jdbc:tc:postgresql:16-alpine:///db"
        }
)

@Sql("/test-data.sql")
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void shouldGetAllProducts(){
        List<ProductEntity> products = productRepository.findAll();
        assertThat(products).hasSize(15);
    }
}