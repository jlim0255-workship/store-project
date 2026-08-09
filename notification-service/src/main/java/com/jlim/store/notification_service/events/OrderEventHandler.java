package com.jlim.store.notification_service.events;

import com.jlim.store.notification_service.domain.models.OrderCreatedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventHandler {
    @RabbitListener(queues = "${notifications.new-orders-queue}")
    void handleOrderCreatedEvent(OrderCreatedEvent event){
        System.out.println("Order Created Event: " + event);
    }
}
