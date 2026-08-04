package com.jlim.store.catalogservice.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

// specify the entity type and primary key
interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findByCode(String code);
}
