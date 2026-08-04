package com.jlim.store.catalogservice.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// specify the entity type and primary key
interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findByCode(String code);
}
