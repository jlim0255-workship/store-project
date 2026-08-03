package com.jlim.store.catalogservice.domain;

import org.springframework.data.jpa.repository.JpaRepository;

// specify the entity type and primary key
interface ProductRepository extends JpaRepository<ProductEntity, Long> {

}
