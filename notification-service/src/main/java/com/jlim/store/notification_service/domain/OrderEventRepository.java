package com.jlim.store.notification_service.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderEventRepository extends JpaRepository<OrderEventEntity, Long> {
    // Jpa built in existsBy prefix, return boolean with the eventId exists or not
    boolean existsByEventId(String eventId);
}
