package com.jlim.store.notification_service.events;

import com.jlim.store.notification_service.domain.NotificationService;
import com.jlim.store.notification_service.domain.OrderEventEntity;
import com.jlim.store.notification_service.domain.OrderEventRepository;
import com.jlim.store.notification_service.domain.models.OrderCancelledEvent;
import com.jlim.store.notification_service.domain.models.OrderCreatedEvent;
import com.jlim.store.notification_service.domain.models.OrderDeliveredEvent;
import com.jlim.store.notification_service.domain.models.OrderErrorEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventHandler {
    private static final Logger log = LoggerFactory.getLogger(OrderEventHandler.class);
    // MEAT: no DTO, or map entity to DTO, we are not publishing the response to web

    // inject notification service here
    private final NotificationService notificationService;
    private final OrderEventRepository orderEventRepository;

    OrderEventHandler(NotificationService notificationService, OrderEventRepository orderEventRepository){
        this.notificationService = notificationService;
        this.orderEventRepository = orderEventRepository;
    }

    @RabbitListener(queues = "${notifications.new-orders-queue}")
    void handleOrderCreatedEvent(OrderCreatedEvent event){
        log.info("Order Created Event: " + event);
        if (orderEventRepository.existsByEventId(event.eventId())){
            log.warn("Received duplicate OrderCreatedEvent with eventId: {}", event.eventId());
            return;
        }
        notificationService.sendOrderCreatedNotification(event);

        // else process new order
        OrderEventEntity orderEvent = new OrderEventEntity(event.eventId());
        orderEventRepository.save(orderEvent);
    }

    @RabbitListener(queues = "${notifications.delivered-orders-queue}")
    void handleOrderDeliveredEvent(OrderDeliveredEvent event){
        log.info("Order Delivered Event: " + event);
        if (orderEventRepository.existsByEventId(event.eventId())){
            log.warn("Received duplicate OrderDeliveredEvent with eventId: {}", event.eventId());
            return;
        }

        // else process new order
        notificationService.sendOrderDeliveredNotification(event);
        OrderEventEntity orderEvent = new OrderEventEntity(event.eventId());
        orderEventRepository.save(orderEvent);
    }

    @RabbitListener(queues = "${notifications.cancelled-orders-queue}")
    void handleOrderCancelledEvent(OrderCancelledEvent event){
        log.info("Order Cancelled Event: " + event);
        if (orderEventRepository.existsByEventId(event.eventId())){
            log.warn("Received duplicate OrderCancelledEvent with eventId: {}", event.eventId());
            return;
        }

        // else process new order
        notificationService.sendOrderCancelledNotification(event);
        OrderEventEntity orderEvent = new OrderEventEntity(event.eventId());
        orderEventRepository.save(orderEvent);
    }

    @RabbitListener(queues = "${notifications.error-orders-queue}")
    void handleOrderErrorEvent(OrderErrorEvent event){
        log.info("Order Error Event: " + event);
        if (orderEventRepository.existsByEventId(event.eventId())){
            log.warn("Received duplicate OrderErrorEvent with eventId: {}", event.eventId());
            return;
        }

        // else process new order
        notificationService.sendOrderErrorEventNotification(event);
        OrderEventEntity orderEvent = new OrderEventEntity(event.eventId());
        orderEventRepository.save(orderEvent);
    }
}
