package com.jlim.store.catalogservice.domain;

import java.math.BigDecimal;

/**
 * MEAT
 * Public exposed: PagedResult, Product(ProductEntity without id), ProductService
 * Internal Private ProductEntity, ProductRepository, ProductMapper (No one calls except Spring Boot)
 * */
public record Product(String code, String name, String description, String imageUrl, BigDecimal price) {}
