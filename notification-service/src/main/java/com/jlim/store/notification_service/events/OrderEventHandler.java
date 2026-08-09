package com.jlim.store.notification_service.events;

import com.jlim.store.notification_service.domain.NotificationService;
import com.jlim.store.notification_service.domain.models.OrderCancelledEvent;
import com.jlim.store.notification_service.domain.models.OrderCreatedEvent;
import com.jlim.store.notification_service.domain.models.OrderDeliveredEvent;
import com.jlim.store.notification_service.domain.models.OrderErrorEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventHandler {

    // inject notification service here
    private final NotificationService notificationService;

    OrderEventHandler(NotificationService notificationService){
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = "${notifications.new-orders-queue}")
    void handleOrderCreatedEvent(OrderCreatedEvent event){
        System.out.println("Order Created Event: " + event);
        notificationService.sendOrderCreatedNotification(event);
    }

    @RabbitListener(queues = "${notifications.delivered-orders-queue}")
    void handleOrderDeliveredEvent(OrderDeliveredEvent event){
        System.out.println("Order Delivered Event: " + event);
        notificationService.sendOrderDeliveredNotification(event);
    }

    @RabbitListener(queues = "${notifications.cancelled-orders-queue}")
    void handleOrderCancelledEvent(OrderCancelledEvent event){
        System.out.println("Order Cancelled Event: " + event);
        notificationService.sendOrderCancelledNotification(event);
    }

    @RabbitListener(queues = "${notifications.error-orders-queue}")
    void handleOrderErrorEvent(OrderErrorEvent event){
        System.out.println("Order Error Event: " + event);
        notificationService.sendOrderErrorEventNotification(event);
    }
}
